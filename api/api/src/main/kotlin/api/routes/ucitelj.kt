package api.routes

import api.extend.client_error
import api.extend.profil
import api.extend.request_info
import api.request.NapakaReq
import api.response.AuditRes
import base.Id
import domain.*
import extend.encrypted
import extend.ime
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject
import serialization.IdSerializer
import services.DbService
import si.urosjarc.server.api.response.StatusUpdateReq
import si.urosjarc.server.api.response.TestUpdateReq
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@Resource("ucitelj")
class ucitelj {

}

fun Route.ucitelj() {
    val db: DbService by this.inject()
    val log = this.logger()

    this.get<ucitelj> {
        val profilRes = this.call.profil()
        val ucitelj = db.ucitelj(id = profilRes.id)
        this.call.respond(ucitelj)
    }
}
