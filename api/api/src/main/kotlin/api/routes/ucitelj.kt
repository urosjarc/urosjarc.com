package api.routes

import api.extend.client_error
import api.extend.profil
import api.extend.system_error
import api.request.TestUstvariReq
import core.services.DbService
import core.use_cases_api.Ustvari_test
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.logging.log4j.kotlin.logger
import org.koin.ktor.ext.inject

@Resource("ucitelj")
class ucitelj {

    @Resource("test")
    class test(val parent: ucitelj)
}

fun Route.ucitelj() {
    val db: DbService by this.inject()
    val log = this.logger()
    val ustvari_test: Ustvari_test by this.inject()

    this.get<ucitelj> {
        val profilRes = this.call.profil()
        val ucitelj = db.ucitelj(id = profilRes.oseba_id)
        this.call.respond(ucitelj)
    }
    this.post<ucitelj.test> {
        val profilRes = this.call.profil()
        val body = this.call.receive<TestUstvariReq>()

        when (val r = ustvari_test.zdaj(
            avtor = profilRes.oseba_id,
            naslov = body.naslov,
            podnaslov = body.podnaslov,
            deadline = body.deadline,
            admini = body.oseba_admini_id,
            ucenci = body.oseba_ucenci_id,
            naloge = body.naloga_id
        )) {
            Ustvari_test.Rezultat.WARN_AVTOR_NE_OBSTAJA -> this.call.client_error(info = "Avtor ne obstaja v bazi!")
            is Ustvari_test.Rezultat.WARN_MANJKAJOCI_ELEMENTI -> this.call.system_error(info = "Nekateri elementi ne obstajajo v sistemu: $r")
            Ustvari_test.Rezultat.ERROR_TEST_SE_NI_SHRANIL -> this.call.system_error(info = "Test se ni ustvaril!")
            is Ustvari_test.Rezultat.PASS -> this.call.respond(r.test)
        }
    }
}
