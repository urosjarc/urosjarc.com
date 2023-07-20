package api.routes

import api.extend.request_info
import api.request.NapakaReq
import app.services.DbService
import domain.Napaka
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

@Resource("")
class index {

    @Resource("napaka")
    class napaka(val parent: index)

}

fun Route.index() {
    val db: DbService by this.inject()
    val log = this.logger()

    this.static {
        this.staticBasePackage = "static"
        this.resources(".")
    }

    this.get<index> {
        this.call.respondText("Hello World!")
    }

    /**
     * Ta route mora zmeraj vrniti success drugace bos ustvaril rekurzijo z clientom.
     */
    this.post<index.napaka> {
        val body = this.call.receive<NapakaReq>()

        val napaka = Napaka(
            tip = body.tip,
            vsebina = body.vsebina,
            dodatno = this.call.request_info()
        )

        napaka.logiraj()
        db.ustvari(napaka)
        this.call.respond(napaka)
    }
}
