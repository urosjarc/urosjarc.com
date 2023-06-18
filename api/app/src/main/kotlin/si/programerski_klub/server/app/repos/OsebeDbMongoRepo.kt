package si.programerski_klub.server.app.repos

import org.litote.kmongo.Id
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.core.domain.uprava.Oseba
import si.programerski_klub.server.core.repos.DbRezultatShranitve
import si.programerski_klub.server.core.repos.OsebeRepo

class OsebeDbMongoRepo(val db: DbMongo) : OsebeRepo {
    override fun ena(id: Id<Oseba>) = this.db.get(id = id)
    override fun vse(): List<Oseba> = this.db.get_all()
    override fun shrani_ali_posodobi(oseba: Oseba): DbRezultatShranitve<Oseba> {
        var nova_oseba = oseba

        val db_oseba = this.db.col<Oseba>().findOne(
            Oseba::ime eq oseba.ime,
            Oseba::priimek eq oseba.priimek,
            Oseba::rojen eq oseba.rojen
        )

        if (db_oseba != null) {
            db_oseba.zdruzi(oseba = oseba)
            nova_oseba = db_oseba
        }

        return this.db.save(nova_oseba)
    }
}
