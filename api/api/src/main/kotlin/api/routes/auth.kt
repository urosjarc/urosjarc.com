package api.routes

import api.extend.*
import api.plugins.Profil
import api.plugins.profil
import base.Env
import com.auth0.jwk.JwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.model.Filters
import domain.Oseba
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject
import services.DbService
import si.urosjarc.server.api.response.PrijavaReq
import si.urosjarc.server.api.response.PrijavaRes
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

@Resource("auth")
class auth {
    @Resource("prijava")
    class prijava(val parent: auth)

    @Resource("profil")
    class profil(val parent: auth)
}

fun Route.auth(jwkProvider: JwkProvider) {

    val db: DbService by this.inject()
    val log = this.logger()

    this.post<auth.prijava> {
        val body = this.call.receive<PrijavaReq>()

        //TODO: Naredi pravilno logiko
        val oseba = db.osebe.find(Filters.eq(Oseba::tip.name, body.username.decrypt().uppercase())).first()
        log.info("Oseba: $oseba")
        //TODO: Naredi pravilno logiko

        val publicKey = jwkProvider.get(Env.JWT_KEYID).publicKey
        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(Env.JWT_PRIVATE_KEY))
        val privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpecPKCS8)
        val token = JWT.create()
            .withAudience(Env.JWT_AUDIENCE)
            .withIssuer(Env.JWT_ISSUER)
            .withClaim(Profil.claim, this.call.zakodiraj(oseba.profil()))
            .withExpiresAt(Date(System.currentTimeMillis() + 5 * 60 * 1000))
            .sign(Algorithm.RSA256(publicKey as RSAPublicKey, privateKey as RSAPrivateKey))

        this.call.respond(PrijavaRes(token = token, tip=oseba.tip))
    }

    this.authenticate {
        this.get<auth.profil> {
            val profilRes = this.call.profil()
            this.call.respond(profilRes)
        }
    }
}
