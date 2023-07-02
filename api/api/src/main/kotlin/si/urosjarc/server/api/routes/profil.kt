package si.urosjarc.server.api.routes

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject
import si.urosjarc.server.api.extend.profil
import si.urosjarc.server.core.domain.Entiteta
import si.urosjarc.server.core.domain.Oseba
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
        val oseba = Entiteta.nakljucni<Oseba>()
        this.call.respond(oseba)
    }

}
