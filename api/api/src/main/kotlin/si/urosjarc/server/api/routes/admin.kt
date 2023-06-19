package si.urosjarc.server.api.routes

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import si.urosjarc.server.api.extend.system_error
import si.urosjarc.server.app.extend.toID
import si.urosjarc.server.core.domain.napredovanje.Postaja
import si.urosjarc.server.core.repos.DbRezultatIskanja
import si.urosjarc.server.core.repos.DbRezultatIzbrisa
import si.urosjarc.server.core.repos.DbRezultatShranitve
import si.urosjarc.server.core.services.DbService


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
    this.get<admin.osebe> {
        this.call.respond(db.osebe.vse())
    }
    this.get<admin.narocila> {
        this.call.respond(db.narocila.vsa())
    }
    this.get<admin.programi> {
        this.call.respond(db.postaje.zacetne())
    }
    this.get<admin.programi.id> {
        when (val r = db.postaje.dedici(it.id.toID())) {
            is DbRezultatIskanja.DATA -> this.call.respond(r.data)
            is DbRezultatIskanja.PASS -> this.call.system_error(r)
        }
    }
    this.post<admin.postaje> {
        val postaje: List<Postaja> = this.call.receive()
        when (val r = db.postaje.shrani(postaje = postaje)) {
            is DbRezultatShranitve.DATA -> this.call.respond(r.data)
            is DbRezultatShranitve.FATAL_DB_NAPAKA -> this.call.system_error(r)
        }
    }

    this.delete<admin.postaje> {
        val postaje: List<String> = this.call.receive()
        when (val r = db.postaje.izbrisi(ids = postaje.map { it.toID() })) {
            DbRezultatIzbrisa.ERROR_DB_IZBRIS_NI_DOVOLJEN -> this.call.system_error(r)
            DbRezultatIzbrisa.PASS -> this.call.respond(postaje)
            DbRezultatIzbrisa.WARN_DELNI_IZBRIS -> this.call.system_error(r)
        }
    }

}
