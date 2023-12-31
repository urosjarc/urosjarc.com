package api.routes

import api.extend.client_error
import api.extend.profil
import api.extend.request_info
import api.request.NapakaReq
import api.response.AuditRes
import core.base.Id
import core.domain.*
import core.extend.encrypted
import core.extend.ime
import core.serialization.IdSerializer
import core.services.DbService
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject
import si.urosjarc.server.api.response.StatusUpdateReq
import si.urosjarc.server.api.response.TestUpdateReq
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@Resource("ucenec")
class ucenec {

    @Resource("napaka")
    class napaka(val parent: ucenec, val stran: Int = 0)

    @Resource("audit")
    class audit(val parent: ucenec, val stran: Int = 0)

    @Resource("test")
    class test(val parent: ucenec) {

        @Resource("{test_id}")
        class test_id(val parent: test, @Serializable(with = IdSerializer::class) val test_id: Id<Test>) {

            @Resource("audit")
            class audit(val parent: test_id)

            @Resource("naloga")
            class naloga(val parent: test_id) {

                @Resource("{naloga_id}")
                class naloga_id(val parent: naloga, @Serializable(with = IdSerializer::class) val naloga_id: Id<Naloga>) {

                    @Resource("audit")
                    class audit(val parent: naloga_id)

                }

            }

        }

    }
}

fun Route.ucenec() {
    val db: DbService by this.inject()
    val log = this.logger()

    this.get<ucenec> {
        val profilRes = this.call.profil()
        val ucenec = db.ucenec(id = profilRes.oseba_id)
        this.call.respond(ucenec)
    }

    this.post<ucenec.test.test_id.naloga.naloga_id> {
        val profil = this.call.profil()
        val body = this.call.receive<StatusUpdateReq>()
        val test_id = it.parent.parent.test_id
        val status: Status =
            when (val status = db.najdi_status(oseba_id = profil.oseba_id, test_id = test_id, naloga_id = it.naloga_id)) {
                null -> {
                    val status = Status(
                        naloga_id = it.naloga_id,
                        test_id = test_id,
                        oseba_id = profil.oseba_id,
                        tip = body.tip,
                        pojasnilo = "".encrypted()
                    )
                    if (db.ustvari(status)) status
                    else return@post this.call.client_error(info = "${ime<Status>()} ni posodobljen!")
                }

                else -> when (val r = db.status_update(
                    id = status._id,
                    oseba_id = profil.oseba_id,
                    test_id = test_id,
                    naloga_id = it.naloga_id,
                    sekund = body.sekund,
                    tip = body.tip
                )) {
                    null -> return@post this.call.client_error(info = "${ime<Status>()} ni posodobljen!")
                    else -> r
                }
            }

        val audit = Audit(
            entitete_id = setOf(profil.oseba_id.vAnyId(), test_id.vAnyId(), it.naloga_id.vAnyId()),
            tip = Audit.Tip.POSODOBITEV_STATUSA_NALOGE,
            trajanje = body.sekund.toDuration(unit = DurationUnit.SECONDS),
            opis = status.tip.name.encrypted(),
            entiteta = ime<Status>().encrypted()
        )

        db.ustvari(audit)
        this.call.respond(AuditRes(status = status, audit = audit))
    }
    this.put<ucenec.test.test_id> {
        val profil = this.call.profil()
        val body = this.call.receive<TestUpdateReq>()
        val test: Test = when (val r = db.test_update(
            id = it.test_id,
            oseba_id = profil.oseba_id,
            datum = body.datum
        )) {
            null -> return@put this.call.client_error(info = "Uporabnik nima dovoljenj!")
            else -> r
        }

        val audit = Audit(
            entitete_id = setOf(profil.oseba_id.vAnyId(), it.test_id.vAnyId()),
            tip = Audit.Tip.POSODOBITEV_STATUSA_NALOGE,
            trajanje = 0.toDuration(unit = DurationUnit.SECONDS),
            opis = body.datum.toString().encrypted(),
            entiteta = ime<Test>().encrypted()
        )

        db.ustvari(audit)

        this.call.respond(AuditRes(test = test, audit = audit))
    }

    this.get<ucenec.test.test_id.naloga.naloga_id.audit> {
        val status_id = it.parent.naloga_id
        val profil = this.call.profil()
        val entity_id = setOf(status_id.vAnyId(), profil.oseba_id.vAnyId())
        this.call.respond(db.audits(entity_id = entity_id, stran = null))
    }
    this.get<ucenec.test.test_id.audit> {
        val test_id = it.parent.test_id
        val profil = this.call.profil()
        val entity_id = setOf(test_id.vAnyId(), profil.oseba_id.vAnyId())
        this.call.respond(db.audits(entity_id = entity_id, stran = null))
    }

    this.get<ucenec.audit> {
        val profil = this.call.profil()
        val entity_id = setOf(profil.oseba_id.vAnyId())
        this.call.respond(db.audits(entity_id = entity_id, stran = it.stran))
    }

    /**
     * Ta route mora zmeraj vrniti success drugace bos ustvaril rekurzijo z clientom.
     */
    this.post<ucenec.napaka> {
        val profil = this.call.profil()
        val body = this.call.receive<NapakaReq>()

        val napaka = Napaka(
            entitete_id = setOf(profil.oseba_id.vAnyId()),
            tip = body.tip,
            vsebina = body.vsebina,
            dodatno = this.call.request_info().encrypted()
        )

        napaka.logiraj()
        db.ustvari(napaka)
        this.call.respond(napaka)
    }

    this.get<ucenec.napaka> {
        val profil = this.call.profil()
        val napake = db.napake(profil.oseba_id.vAnyId(), stran = it.stran)
        this.call.respond(napake)
    }

}
