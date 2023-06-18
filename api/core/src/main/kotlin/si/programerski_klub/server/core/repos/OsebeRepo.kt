package si.programerski_klub.server.core.repos

import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.uprava.Oseba

interface OsebeRepo {
    fun ena(id: Id<Oseba>): DbRezultatId<Oseba>
    fun vse(): List<Oseba>
    fun shrani_ali_posodobi(oseba: Oseba): DbRezultatShranitve<Oseba>
}
