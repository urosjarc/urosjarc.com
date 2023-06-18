package si.programerski_klub.server.app.repos

import org.litote.kmongo.Id
import org.litote.kmongo.find
import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.app.services.OR
import si.programerski_klub.server.app.services.str
import si.programerski_klub.server.core.domain.placevanje.Narocilo
import si.programerski_klub.server.core.domain.uprava.Oseba
import si.programerski_klub.server.core.repos.DbRezultatShranitve
import si.programerski_klub.server.core.repos.NarocilaRepo

class NarocilaDbMongoRepo(val db: DbMongo) : NarocilaRepo {
    override fun vsa(): List<Narocilo> = this.db.get_all()
    override fun eno(id: Id<Narocilo>) = this.db.get(id = id)
    override fun shrani(narocilo: Narocilo): DbRezultatShranitve<Narocilo> {
        narocilo.prejemnik = when (val r = this.db.osebe.shrani_ali_posodobi(oseba = narocilo.prejemnik)) {
            is DbRezultatShranitve.DATA -> r.data
            is DbRezultatShranitve.FATAL_DB_NAPAKA -> return DbRezultatShranitve.FATAL_DB_NAPAKA()
        }

        narocilo.placnik = narocilo.placnik?.let {
            this.db.osebe.shrani_ali_posodobi(oseba = it)
            when (val r = this.db.osebe.shrani_ali_posodobi(oseba = it)) {
                is DbRezultatShranitve.DATA -> r.data
                is DbRezultatShranitve.FATAL_DB_NAPAKA -> return DbRezultatShranitve.FATAL_DB_NAPAKA()
            }
        }

        return this.db.save(entiteta = narocilo)
    }

    override fun vsa(id: Id<Oseba>): List<Narocilo> {
        val query0 = "{\"${Narocilo::placnik.name}._id\": ${id.str()}}"
        val query1 = "{\"${Narocilo::prejemnik.name}._id\": ${id.str()}}"
        val query2 = "{$OR: [$query0, $query1]}"
        return this.db.col<Narocilo>().find(query2).toList()
    }
}
