package si.urosjarc.server.api.routes

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Resource("")
class index

fun Route.index() {

    this.static {
        this.staticBasePackage = "static"
        this.resources(".")
    }

    this.get<index> {
        this.call.respondText("Hello World!")
    }
}
