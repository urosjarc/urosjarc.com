package api.routes

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import app.services.DbService


@Resource("admin")
class admin {

    @Resource("osebe")
    class osebe(val parent: admin)

    @Resource("narocila")
    class narocila(val parent: admin)

    @Resource("programi")
    class programi(val parent: admin) {

        @Resource("{id}")
        class id(val parent: programi, val id: String)

    }

    @Resource("postaje")
    class postaje(val parent: admin)

}

fun Route.admin() {
    val db: DbService by this.inject()

    this.get<admin> {
        this.call.respond("OK YOU ARE ADMIN :)")
    }

}
