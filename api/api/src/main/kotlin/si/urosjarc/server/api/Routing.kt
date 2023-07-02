package si.urosjarc.plugins

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import si.urosjarc.server.api.extend.client_unauthorized
import si.urosjarc.server.api.models.Profil
import si.urosjarc.server.api.plugins.PreveriProfil
import si.urosjarc.server.api.routes.admin
import si.urosjarc.server.api.routes.auth
import si.urosjarc.server.api.routes.index
import si.urosjarc.server.api.routes.profil
import si.urosjarc.server.app.base.App
import si.urosjarc.server.core.base.Env
import si.urosjarc.server.core.domain.Oseba
import java.util.concurrent.TimeUnit

fun Application.configureRouting() {
    this.install(CallLogging) {
        this.level = org.slf4j.event.Level.INFO
    }
    this.install(CORS) {
        this.allowHeader(HttpHeaders.ContentType)
        this.allowHeader(HttpHeaders.Authorization)
        this.anyHost()
        this.allowMethod(HttpMethod.Post)
        this.allowMethod(HttpMethod.Get)
        this.allowMethod(HttpMethod.Delete)
    }
    this.install(Resources)
    this.install(ContentNegotiation) {
        this.json(Json {
            this.prettyPrint = true
            this.isLenient = true
            this.allowSpecialFloatingPointValues = true
        })
    }
    this.install(Koin) {
        this.slf4jLogger(level = org.koin.core.logger.Level.INFO)
        this.modules(modules = App.modul(tip = App.Tip.TEST))
    }


    val jwkProvider = JwkProviderBuilder(Env.JWT_ISSUER)
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    this.install(Authentication) {

        this.jwt {
            this.realm = Env.JWT_REALM
            this.verifier(jwkProvider, Env.JWT_ISSUER) {
                this.acceptLeeway(3)
            }
            this.validate { credential ->
                val claim = credential.payload.getClaim(Profil.claim)
                if (claim != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            this.challenge { defaultScheme, realm -> this.call.client_unauthorized() }
        }
    }

    this.routing {

        //PUBLIC ROUTES
        this.index()

        //AUTH ROUTES
        this.auth(jwkProvider = jwkProvider)

        //PRIVATE ROUTES
        this.authenticate {
            this.profil()
        }

        this.authenticate {
            this.install(PreveriProfil) {
                this.tip_profila = listOf(Oseba.Tip.ADMIN)
            }
            this.admin()
        }
    }

    val db: DbService by this.inject()
}
