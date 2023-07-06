package api.routes

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject
import api.extend.profil
import app.services.DbService
import core.domain.Oseba


@Resource("profil")
class profil {
    @Resource("oseba")
    class oseba(val parent: profil)
}

fun Route.profil() {
    val db: DbService by this.inject()

    val log = this.logger()

    this.get<profil> {
        val profil = this.call.profil()
        println(profil)
        val oseba_profil = db.osebaRepo.profil(id = profil.id)
        this.call.respond(oseba_profil)
    }

    this.get<profil.oseba> {
        val profil = this.call.profil()
        val oseba = db.nakljucni<Oseba>()
        this.call.respond(oseba)
    }

}
