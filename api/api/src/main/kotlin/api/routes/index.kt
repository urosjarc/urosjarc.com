package api.routes

import api.extend.client_error
import api.extend.request_info
import api.request.NapakaReq
import api.response.IndexRes
import api.response.KontaktObrazecRes
import domain.Napaka
import extend.encrypted
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject
import services.DbService
import si.urosjarc.server.api.response.KontaktObrazecReq
import use_cases.Ustvari_testne_podatke
import use_cases_api.Sprejmi_kontaktni_obrazec

@Resource("")
class index {

    @Resource("napaka")
    class napaka(val parent: index)

    @Resource("kontakt")
    class kontakt(val parent: index)

}

fun Route.index() {
    val db: DbService by this.inject()
    val log = this.logger()
    val sprejmi_kontaktni_obrazec: Sprejmi_kontaktni_obrazec by this.inject()
    val ustvariTestnePodatke: Ustvari_testne_podatke by this.inject()

    this.static {
        this.staticBasePackage = "static"
        this.resources(".")
    }

    this.get<index> {
        val indexRes = ustvariTestnePodatke.nakljucni<IndexRes>()
        this.call.respond(indexRes)
    }

    /**
     * Ta route mora zmeraj vrniti success drugace bos ustvaril rekurzijo z clientom.
     */
    this.post<index.napaka> {
        val body = this.call.receive<NapakaReq>()

        val napaka = Napaka(
            tip = body.tip,
            vsebina = body.vsebina.encrypted(),
            dodatno = this.call.request_info().encrypted(),
            entitete_id = setOf()
        )

        napaka.logiraj()
        db.ustvari(napaka)
        this.call.respond(napaka)
    }

    this.post<index.kontakt> {
        val body = this.call.receive<KontaktObrazecReq>()
        when (val r = sprejmi_kontaktni_obrazec.exe(
            ime_priimek = body.ime_priimek,
            email = body.email,
            telefon = body.telefon,
            vsebina = body.vsebina
        )) {
            is Sprejmi_kontaktni_obrazec.Rezultat.WARN -> this.call.client_error(r.info)
            is Sprejmi_kontaktni_obrazec.Rezultat.PASS -> this.call.respond(
                KontaktObrazecRes(
                    oseba = r.obrazec.oseba,
                    telefon = r.obrazec.telefon,
                    email = r.obrazec.email,
                    sporocila = r.sporocila
                )
            )
        }
    }
}
