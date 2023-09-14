package api.routes

import api.extend.profil
import core.services.DbService
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


@Resource("admin")
class admin

fun Route.admin() {
    val db: DbService by this.inject()

    this.get<admin> {
        val profilRes = this.call.profil()
        val admin = db.admin(id = profilRes.oseba_id)
        this.call.respond(admin)
    }

}
