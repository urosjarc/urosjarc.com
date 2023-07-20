package api.extend

import api.plugins.Profil
import api.response.ErrorRes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.util.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun ApplicationCall.profil(): Profil {
    val principal = this.principal<JWTPrincipal>()
    val data = principal!!.payload.getClaim(Profil.claim).asString().toString()
    return Json.decodeFromString(data)
}

suspend fun ApplicationCall.client_error(cls: Any? = null, info: String? = null) {
    return this.respond(
        status = HttpStatusCode.BadRequest,
        message = ErrorRes(
            napaka = ErrorRes.Tip.UPORABNISKA,
            razred = if (cls != null) cls::class.simpleName.toString() else null,
            info = info
        )
    )
}

fun ApplicationCall.request_info(): String = Json.encodeToString(this.request.headers.toMap())


suspend fun ApplicationCall.client_unauthorized() {
    val status = HttpStatusCode.Unauthorized
    return this.respond(
        status = status,
        message = ErrorRes(
            napaka = ErrorRes.Tip.UPORABNISKA,
            razred = status.description,
            info = null
        )
    )
}

suspend fun ApplicationCall.system_error(cls: Any, info: String? = null) {
    return this.respond(
        status = HttpStatusCode.InternalServerError,
        message = ErrorRes(
            napaka = ErrorRes.Tip.SISTEMSKA,
            razred = cls::class.simpleName.toString(),
            info = info
        )
    )
}
