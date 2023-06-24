package si.urosjarc.server.api.routes

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject
import si.urosjarc.server.api.extend.profil
import si.urosjarc.server.api.extend.system_error
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.repos.DbGetRezultat
import si.urosjarc.server.core.services.DbService


@Resource("profil")
class profil {

    @Resource("oseba")
    class oseba(val parent: profil)
}

fun Route.profil() {
    val db: DbService by this.inject()
    val log = this.logger()

    this.get<profil> {
        this.call.respond("OK YOU ARE ADMIN :)")
    }

    this.get<profil.oseba> {
        val profil = this.call.profil()

        var osebaR: DbGetRezultat<Oseba>? = null
        db.exe {
            osebaR = db.osebaRepo.get(key = Id(profil.id))
            log.info(osebaR.toString())
        }

        when (val r = osebaR) {
            is DbGetRezultat.DATA -> this.call.respond(r.data)
            is DbGetRezultat.ERROR -> this.call.system_error(r)
            null -> this.call.system_error(profil)
        }
    }

}
