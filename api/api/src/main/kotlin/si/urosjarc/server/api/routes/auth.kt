package si.urosjarc.server.api.routes

import com.auth0.jwk.JwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import si.urosjarc.server.api.extend.profil
import si.urosjarc.server.api.models.Profil
import si.urosjarc.server.api.models.profil
import si.urosjarc.server.api.response.PrijavaReq
import si.urosjarc.server.app.base.Env
import si.urosjarc.server.core.domain.uprava.Oseba
import si.urosjarc.server.core.services.DbService
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

@Resource("auth")
class auth {
    @Resource("prijava")
    class prijava(val parent: auth)

    @Resource("whois")
    class whois(val parent: auth)
}

fun Route.auth(jwkProvider: JwkProvider) {

    val db by this.inject<DbService>()

    this.post<auth.prijava> {
        val body = this.call.receive<PrijavaReq>()

        //TODO: Naredi pravilno logiko
        val oseba: Oseba = db.osebe.vse().first()
        //TODO: Naredi pravilno logiko

        val publicKey = jwkProvider.get("6f8856ed-9189-488f-9011-0ff4b6c08edc").publicKey
        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(Env.JWT_PRIVATE_KEY))
        val privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpecPKCS8)
        val token = JWT.create()
            .withAudience(Env.JWT_AUDIENCE)
            .withIssuer(Env.JWT_ISSUER)
            .withClaim(Profil.claim, Json.encodeToString(oseba.profil()))
            .withExpiresAt(Date(System.currentTimeMillis() + 5 * 60000))
            .sign(Algorithm.RSA256(publicKey as RSAPublicKey, privateKey as RSAPrivateKey))

        this.call.respond(mapOf("token" to token))
    }

    this.authenticate {
        this.get<auth.whois> {
            val profil = this.call.profil()
            this.call.respond(profil)
        }
    }
}