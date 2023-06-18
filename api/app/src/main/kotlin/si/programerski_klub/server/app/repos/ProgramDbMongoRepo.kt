package si.programerski_klub.server.app.repos

import org.litote.kmongo.Id
import org.litote.kmongo.find
import org.litote.kmongo.findOne
import si.programerski_klub.server.app.services.AND
import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.app.services.ID
import si.programerski_klub.server.app.services.str
import si.programerski_klub.server.core.domain.napredovanje.Program
import si.programerski_klub.server.core.domain.uprava.Oseba
import si.programerski_klub.server.core.repos.DbRezultatId
import si.programerski_klub.server.core.repos.ProgramiRepo

class ProgramDbMongoRepo(val db: DbMongo) : ProgramiRepo {

    override fun shrani(program: Program) = this.db.save(entiteta = program)
    override fun en(id: Id<Program>, uporabnik: Id<Oseba>): DbRezultatId<Program> {
        val query0 = "{$ID: ${id.str()}}"
        val query1 = "{\"${Program::uporabnik.name}\": ${uporabnik.str()}}"
        val query2 = "{$AND: [$query0, $query1]}"
        println(query2)
        return when (val r = this.db.col<Program>().findOne(query2)) {
            null -> DbRezultatId.ERROR()
            else -> DbRezultatId.DATA(data = r)
        }
    }

    override fun vsi(id: Id<Oseba>): List<Program> {
        val query = "{\"${Program::uporabnik.name}\": ${id.str()}}"
        return this.db.col<Program>().find(query).toList()
    }
}
