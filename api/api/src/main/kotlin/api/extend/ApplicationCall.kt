package api.extend

import api.plugins.Profil
import api.response.ErrorRes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.util.*
import org.koin.ktor.ext.inject
import services.JsonService

inline fun <reified T> ApplicationCall.dekodiraj(value: String): T {
    val json by this.inject<JsonService>()
    return json.dekodiraj(value = value)
}

inline fun <reified T> ApplicationCall.zakodiraj(obj: T): String {
    val json by this.inject<JsonService>()
    return json.zakodiraj(value = obj)
}


fun ApplicationCall.request_info(): String {
    return this.zakodiraj(this.request.headers.toMap())
}

fun ApplicationCall.profil(): Profil {
    val principal = this.principal<JWTPrincipal>()
    val data = principal!!.payload.getClaim(Profil.claim).asString().toString()
    return this.dekodiraj(data)
}

suspend fun ApplicationCall.client_error(info: String) {
    val status = HttpStatusCode.BadRequest
    return this.respond(
        status = HttpStatusCode.BadRequest,
        message = ErrorRes(
            napaka = ErrorRes.Tip.UPORABNISKA,
            status = status.description,
            info = info
        )
    )
}

suspend fun ApplicationCall.client_unauthorized() {
    val status = HttpStatusCode.Unauthorized
    return this.respond(
        status = status,
        message = ErrorRes(
            napaka = ErrorRes.Tip.UPORABNISKA,
            status = status.description,
            info = "Uporabnik ni avtoriziran!"
        )
    )
}

suspend fun ApplicationCall.system_error(info: String) {
    val status = HttpStatusCode.InternalServerError
    return this.respond(
        status = HttpStatusCode.InternalServerError,
        message = ErrorRes(
            napaka = ErrorRes.Tip.SISTEMSKA,
            status = status.description,
            info = info
        )
    )
}
