package api.routes

import api.extend.client_error
import api.extend.profil
import api.extend.request_info
import api.request.NapakaReq
import base.Id
import domain.Napaka
import domain.Status
import domain.Test
import extend.ime
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject
import services.DbService
import si.urosjarc.server.api.response.StatusUpdateReq
import si.urosjarc.server.api.response.TestUpdateReq


@Resource("profil")
class profil {

    @Resource("napaka")
    class napaka(val parent: profil, val stran: Int = 0)

    @Resource("audit")
    class audit(val parent: profil, val stran: Int = 0)

    @Resource("test")
    class test(val parent: profil) {

        @Resource("{test_id}")
        class test_id(val parent: test, val test_id: Id<Test>) {

            @Resource("audit")
            class audit(val parent: test_id)

            @Resource("status")
            class status(val parent: test_id) {

                @Resource("{status_id}")
                class status_id(val parent: status, val status_id: Id<Status>) {

                    @Resource("audit")
                    class audit(val parent: status_id)

                }

            }

        }

    }
}

fun Route.profil() {
    val db: DbService by this.inject()
    val log = this.logger()

    this.get<profil> {
        val profilRes = this.call.profil()
        val oseba_profil = db.profil(id = profilRes.id)
        this.call.respond(oseba_profil)
    }

    this.put<profil.test.test_id.status.status_id> {
        val profil = this.call.profil()
        val body = this.call.receive<StatusUpdateReq>()
        val test_id = it.parent.parent.test_id
        when (db.status_obstaja(id = it.status_id, oseba_id = profil.id, test_id = test_id)) {
            false -> this.call.client_error(info = "${ime<Status>()} ne obstaja!")
            true -> when (val r = db.status_update(
                id = it.status_id,
                oseba_id = profil.id,
                test_id = it.parent.parent.test_id,
                sekund = body.sekund,
                tip = body.tip
            )) {
                null -> this.call.client_error(info = "${ime<Status>()} ni posodobljen!")
                else -> this.call.respond(r)
            }
        }
    }
    this.put<profil.test.test_id> {
        val profil = this.call.profil()
        val body = this.call.receive<TestUpdateReq>()
        when (val r = db.test_update(
            id = it.test_id,
            oseba_id = profil.id,
            datum = body.datum
        )) {
            null -> this.call.client_error(info = "${ime<Status>()} ni posodobljen!")
            else -> this.call.respond(r)
        }
    }

    this.get<profil.test.test_id.status.status_id.audit> {
        val status_id = it.parent.status_id
        this.call.respond(db.audits(entity_id = status_id.vAnyId(), stran = null))
    }
    this.get<profil.test.test_id.audit> {
        val test_id = it.parent.test_id
        this.call.respond(db.audits(entity_id = test_id.vAnyId(), stran = null))
    }

    this.get<profil.audit> {
        val profil = this.call.profil()
        this.call.respond(db.audits(entity_id = profil.id.vAnyId(), stran = it.stran))
    }

    /**
     * Ta route mora zmeraj vrniti success drugace bos ustvaril rekurzijo z clientom.
     */
    this.post<profil.napaka> {
        val profil = this.call.profil()
        val body = this.call.receive<NapakaReq>()

        val napaka = Napaka(
            entitete_id = listOf(profil.id).map { it.vAnyId() },
            tip = body.tip,
            vsebina = body.vsebina,
            dodatno = this.call.request_info()
        )

        napaka.logiraj()
        db.ustvari(napaka)
        this.call.respond(napaka)
    }

    this.get<profil.napaka> {
        val profil = this.call.profil()
        val napake = db.napake(profil.id.vAnyId(), stran = it.stran)
        this.call.respond(napake)
    }

}
