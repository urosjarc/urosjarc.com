package si.programerski_klub.server.core.repos

import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.napredovanje.Program
import si.programerski_klub.server.core.domain.uprava.Oseba

interface ProgramiRepo {
    fun vsi(id: Id<Oseba>): List<Program>
    fun en(id: Id<Program>, oseba: Id<Oseba>): DbRezultatId<Program>
    fun shrani(program: Program): DbRezultatShranitve<Program>
}
