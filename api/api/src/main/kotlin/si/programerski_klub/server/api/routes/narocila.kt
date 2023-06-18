package si.programerski_klub.server.api.routes

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import org.litote.kmongo.toId
import si.programerski_klub.server.api.extend.client_error
import si.programerski_klub.server.api.extend.system_error
import si.programerski_klub.server.api.request.NarociloReq
import si.programerski_klub.server.core.domain.placevanje.Izbira
import si.programerski_klub.server.core.domain.placevanje.Narocilo
import si.programerski_klub.server.core.repos.DbRezultatId
import si.programerski_klub.server.core.services.DbService
import si.programerski_klub.server.core.use_cases_api.Sprejmi_narocilo

@Resource("narocila")
class narocila

fun Route.narocila() {
    val sprejmi_narocilo: Sprejmi_narocilo by this.inject()
    val db: DbService by this.inject()

    this.post<narocila> {

        //PREPARING REQ TO BE PROCESSED
        val body = this.call.receive<NarociloReq>()
        val izbire = mutableSetOf<Izbira>()
        for (izbira in body.izbire) {
            when (val r = db.produkti.en(id = izbira.produkt.toId())) {
                is DbRezultatId.DATA -> izbire.add(
                    Izbira(produkt = r.data, kolicina = izbira.kolicina)
                )

                is DbRezultatId.ERROR -> return@post this.call.system_error(r)
            }
        }

        val narocilo = Narocilo(
            kosarica = izbire,
            placnik = body.skrbnik,
            prejemnik = body.clan
        )

        when (val r = sprejmi_narocilo.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.DATA -> this.call.respond(r.data)

            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> this.call.client_error(r, r.kontakt.data)
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> this.call.client_error(r, r.kontakt.data)
            Sprejmi_narocilo.Rezultat.WARN_PREJEMNIK_JE_PLACNIK -> this.call.client_error(r)

            Sprejmi_narocilo.Rezultat.ERROR_PREVELIKO_NAROCILO -> this.call.system_error(r)
            is Sprejmi_narocilo.Rezultat.ERROR_PRODUKT_NE_OBSTAJA -> this.call.system_error(r, r.id.toString())
            is Sprejmi_narocilo.Rezultat.ERROR_UPORABNIK_IMA_ZE_NAROCNINO -> this.call.system_error(r, r.narocnina.toString())
            Sprejmi_narocilo.Rezultat.FATAL_NAROCNINA_SE_NI_SHRANILA -> this.call.system_error(r)
        }
    }
}
