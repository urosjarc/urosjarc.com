package api.routes

import api.extend.client_error
import api.extend.profil
import app.services.DbService
import domain.Audit
import domain.Status
import extends.ime
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject
import si.urosjarc.server.api.response.StatusUpdateReq


@Resource("profil")
class profil {
    @Resource("test")
    class test(val parent: profil) {

        @Resource("{test_id}")
        class test_id(val parent: test, val test_id: String) {

            @Resource("status")
            class status(val parent: test_id) {

                @Resource("{status_id}")
                class status_id(val parent: status, val status_id: String) {

                    @Resource("audits")
                    class audits(val parent: status_id, val stran: Int=0)

                }

            }

        }

    }
}

fun Route.profil() {
    val db: DbService by this.inject()

    val log = this.logger()

    this.get<profil> {
        val profil = this.call.profil()
        val oseba_profil = db.osebaRepo.profil(id = profil.id)
        this.call.respond(oseba_profil)
    }

    this.put<profil.test.test_id.status.status_id> {
        val profil = this.call.profil()
        val body = this.call.receive<StatusUpdateReq>()
        val test_id = it.parent.parent.test_id
        val status_id = it.status_id
        when (db.osebaRepo.status_obstaja(id = profil.id, test_id = test_id, status_id = status_id)) {
            true -> this.call.client_error(info = "${ime<Status>()} ne obstaja!")
            false -> when (val r =
                db.statusRepo.posodobi_tip(id = it.status_id, test_id = it.parent.parent.test_id, tip = body.tip)) {
                null -> this.call.client_error(info = "${ime<Status>()} ni posodobljen!")
                else -> this.call.respond(r)
            }
        }
    }

    this.get<profil.test.test_id.status.status_id.audits> {
        val status_id = it.parent.status_id
        val stran = it.stran
        this.call.respond(db.auditRepo.dobi(entity_id=status_id))
    }

}
