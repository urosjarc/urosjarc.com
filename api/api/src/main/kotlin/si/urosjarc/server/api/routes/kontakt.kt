package si.urosjarc.server.api.routes

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import si.urosjarc.server.api.extend.client_error
import si.urosjarc.server.api.extend.system_error
import si.urosjarc.server.core.domain.obvescanje.KontaktniObrazec


@Resource("kontakt")
class kontakt()


fun Route.kontakt() {
    val sprejmi_kontaktni_obrazec: Sprejmi_kontakti_obrazec by this.inject()

    this.post<kontakt> {
        val body = this.call.receive<KontaktniObrazec>()
        when (val r = sprejmi_kontaktni_obrazec.zdaj(kontaktni_obrazec = body)) {
            is Sprejmi_kontakti_obrazec.Rezultat.DATA -> this.call.respond(r.kontaktni_obrazec)
            is Sprejmi_kontakti_obrazec.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> this.call.client_error(r, r.kontakt.data)
            is Sprejmi_kontakti_obrazec.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> this.call.client_error(r, r.kontakt.data)
            Sprejmi_kontakti_obrazec.Rezultat.FATAL_KONTAKTNI_OBRAZEC_SE_NI_SHRANIL -> this.call.system_error(r)
        }
    }
}
