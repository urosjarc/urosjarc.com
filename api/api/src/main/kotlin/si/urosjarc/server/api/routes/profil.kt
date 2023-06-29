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
import si.urosjarc.server.core.base.DbDobiRezultat
import si.urosjarc.server.core.services.DbService


@Resource("profil")
class profil {

    @Resource("oseba")
    class oseba(val parent: profil)

    @Resource("ucenje")
    class ucenje(val parent: profil)

    @Resource("sporocila")
    class sporocila(val parent: profil)

    @Resource("statusi")
    class statusi(val parent: profil)
}

fun Route.profil() {
    val db: DbService by this.inject()
    val log = this.logger()

    this.get<profil> {
        this.call.respond("OK YOU ARE ADMIN :)")
    }

    this.get<profil.oseba> {
        val profil = this.call.profil()

        val result = db.izvedi { db.osebaRepo.dobi(kljuc = Id(profil.id)) }
        when (val r = result) {
            is DbDobiRezultat.DATA -> this.call.respond(r.data)
            is DbDobiRezultat.ERROR -> this.call.system_error(r)
        }
    }

    this.get<profil.ucenje> {
        val profil = this.call.profil()
        val json = db.izvedi { db.ucenjeRepo.dobi_ucence(id_ucitelja = Id(profil.id)) }
        this.call.respond(json)
    }

    this.get<profil.sporocila> {
        val profil = this.call.profil()
        val json = db.izvedi { db.sporociloRepo.dobi_posiljatelje(id_prejemnika = Id(profil.id)) }
        this.call.respond(json)
    }

    this.get<profil.statusi> {
        val profil = this.call.profil()
        val json = db.izvedi { db.statusRepo.dobi_statuse(id_osebe = Id(profil.id)) }
        this.call.respond(json)
    }

}
