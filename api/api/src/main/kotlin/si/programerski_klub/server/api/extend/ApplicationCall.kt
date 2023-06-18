package si.programerski_klub.server.api.extend

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import si.programerski_klub.server.api.models.Profil
import si.programerski_klub.server.api.response.ErrorRes

fun ApplicationCall.profil(): Profil {
    val principal = this.principal<JWTPrincipal>()
    val data = principal!!.payload.getClaim(Profil.claim).asString().toString()
    return Json.decodeFromString(data)
}

suspend fun ApplicationCall.client_error(cls: Any, info: String? = null) {
    return this.respond(
        status = HttpStatusCode.BadRequest,
        message = ErrorRes(
            napaka = ErrorRes.Tip.UPORABNISKA,
            rezultat = cls::class.simpleName.toString(),
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
            rezultat = status.description,
            info = null
        )
    )
}

suspend fun ApplicationCall.system_error(cls: Any, info: String? = null) {
    return this.respond(
        status = HttpStatusCode.InternalServerError,
        message = ErrorRes(
            napaka = ErrorRes.Tip.SISTEMSKA,
            rezultat = cls::class.simpleName.toString(),
            info = info
        )
    )
}
