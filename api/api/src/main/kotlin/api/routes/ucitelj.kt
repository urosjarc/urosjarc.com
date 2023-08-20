package api.routes

import api.extend.profil
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject
import services.DbService


@Resource("ucitelj")
class ucitelj {

}

fun Route.ucitelj() {
    val db: DbService by this.inject()
    val log = this.logger()

    this.get<ucitelj> {
        val profilRes = this.call.profil()
        val ucitelj = db.ucitelj(id = profilRes.oseba_id)
        this.call.respond(ucitelj)
    }
}
