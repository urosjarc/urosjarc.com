package api.routes

import api.extend.profil
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import services.DbService


@Resource("admin")
class admin {

}

fun Route.admin() {
    val db: DbService by this.inject()

    this.get<admin> {
        val profilRes = this.call.profil()
        val admin = db.admin(id = profilRes.id)
        this.call.respond(admin)
    }

}
