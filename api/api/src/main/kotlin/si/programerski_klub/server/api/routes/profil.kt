package si.programerski_klub.server.api.routes

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.toId
import si.programerski_klub.server.api.extend.client_error
import si.programerski_klub.server.api.extend.profil
import si.programerski_klub.server.api.extend.system_error
import si.programerski_klub.server.core.repos.DbRezultatId
import si.programerski_klub.server.core.services.DbService

@Resource("profil")
class profil {

    @Resource("narocnine")
    class narocnine(val parent: profil)

    @Resource("narocila")
    class narocila(val parent: profil)

    @Resource("programi")
    class programi(val parent: profil) {

        @Resource("{id}")
        class id(val parent: programi, val id: String)
    }

    @Resource("postaje")
    class postaje(val parent: profil) {

        @Resource("{id}")
        class id(val parent: postaje, val id: String) {

            @Resource("naprej")
            class narprej(val parent: id)

        }

    }

    @Resource("produkti")
    class produkti(val parent: profil) {

        @Resource("{id}")
        class id(val parent: produkti, val id: String)

    }

    @Resource("ponudbe")
    class ponudbe(val parent: profil) {

        @Resource("{id}")
        class id(val parent: ponudbe, val id: String)

    }
}

fun Route.profil() {
    val db: DbService by this.inject()

    this.get<profil.narocnine> {
        val profil = this.call.profil()
        val narocnine = db.narocnine.vse(id = profil.id)
        this.call.respond(narocnine)
    }

    this.get<profil.ponudbe> {
        this.call.respond(db.ponudbe.vse())
    }

    this.get<profil.ponudbe.id> {
        when (val r = db.produkti.vsi(id = it.id.toId())) {
            is DbRezultatId.DATA -> this.call.respond(r.data)
            is DbRezultatId.ERROR -> this.call.client_error(r)
        }
    }

    this.get<profil.produkti.id> {
        when (val r = db.produkti.en(id = it.id.toId())) {
            is DbRezultatId.ERROR -> this.call.system_error(r)
            is DbRezultatId.DATA -> this.call.respond(r.data)
        }
    }

    this.get<profil.narocila> {
        val profil = this.call.profil()
        val narocila = db.narocila.vsa(id = profil.id)
        this.call.respond(narocila)
    }

    this.get<profil.programi> {
        val profil = this.call.profil()
        val program = db.programi.vsi(id = profil.id)
        this.call.respond(program)
    }

    this.get<profil.programi.id> {
        val profil = this.call.profil()
        when (val r = db.programi.en(id = it.id.toId(), oseba = profil.id)) {
            is DbRezultatId.DATA -> this.call.respond(r.data)
            is DbRezultatId.ERROR -> this.call.system_error(r)
        }
    }

    this.get<profil.postaje.id> {
        when (val r = db.postaje.ena(id = it.id.toId())) {
            is DbRezultatId.DATA -> this.call.respond(r.data)
            is DbRezultatId.ERROR -> this.call.system_error(r)
        }
    }

    this.get<profil.postaje.id.narprej> {
        when (val r = db.postaje.naslednje(id = it.parent.id.toId())) {
            is DbRezultatId.DATA -> this.call.respond(r.data)
            is DbRezultatId.ERROR -> this.call.system_error(r)
        }
    }
}
