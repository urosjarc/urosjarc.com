package si.urosjarc.server.api.routes

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.toId
import si.urosjarc.server.api.extend.client_error
import si.urosjarc.server.api.extend.system_error
import si.urosjarc.server.api.request.VclanitevReq
import si.urosjarc.server.core.domain.placevanje.Izbira
import si.urosjarc.server.core.domain.placevanje.Narocilo
import si.urosjarc.server.core.domain.placevanje.Produkt
import si.urosjarc.server.core.repos.DbRezultatId
import si.urosjarc.server.core.services.DbService


@Resource("clanarine")
class clanarine

fun Route.clanarine() {

    val db by this.inject<DbService>()
    val sprejmi_narocnino by this.inject<Sprejmi_narocilo>()


    this.get<clanarine> {
        this.call.respond(db.produkti.vsi(tip = Produkt.Tip.CLANARINA))
    }

    this.post<clanarine> {
        //PREPARING REQ TO BE PROCESSED
        val req = this.call.receive<VclanitevReq>()
        val produkt = when (val r = db.produkti.en(id = req.clanarina.toId())) {
            is DbRezultatId.DATA -> r.data
            is DbRezultatId.ERROR -> return@post this.call.system_error(r)
        }
        val narocilo = Narocilo(
            kosarica = setOf(Izbira(produkt, kolicina = 1)),
            placnik = req.skrbnik,
            prejemnik = req.clan
        )

        when (val r = sprejmi_narocnino.zdaj(narocilo = narocilo)) {
            is Sprejmi_narocilo.Rezultat.DATA -> this.call.respond(r.data)
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> this.call.client_error(r)
            is Sprejmi_narocilo.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> this.call.client_error(r)
            Sprejmi_narocilo.Rezultat.WARN_PREJEMNIK_JE_PLACNIK -> this.call.client_error(r)
            Sprejmi_narocilo.Rezultat.ERROR_PREVELIKO_NAROCILO -> this.call.system_error(r)
            is Sprejmi_narocilo.Rezultat.ERROR_PRODUKT_NE_OBSTAJA -> this.call.system_error(r)
            is Sprejmi_narocilo.Rezultat.ERROR_UPORABNIK_IMA_ZE_NAROCNINO -> this.call.system_error(r)
            Sprejmi_narocilo.Rezultat.FATAL_NAROCNINA_SE_NI_SHRANILA -> this.call.system_error(r)
        }
    }

}
