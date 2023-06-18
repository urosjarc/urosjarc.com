package si.programerski_klub.server.core.repos

import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.placevanje.Narocilo
import si.programerski_klub.server.core.domain.uprava.Oseba

interface NarocilaRepo {
    fun vsa(): List<Narocilo>
    fun eno(id: Id<Narocilo>): DbRezultatId<Narocilo>
    fun shrani(narocilo: Narocilo): DbRezultatShranitve<Narocilo>
    fun vsa(id: Id<Oseba>): List<Narocilo>
}
