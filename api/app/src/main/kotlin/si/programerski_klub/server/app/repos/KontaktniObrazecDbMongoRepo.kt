package si.programerski_klub.server.app.repos

import org.litote.kmongo.Id
import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.core.domain.obvescanje.KontaktniObrazec
import si.programerski_klub.server.core.repos.KontaktniObrazecRepo

class KontaktniObrazecDbMongoRepo(val db: DbMongo) : KontaktniObrazecRepo {
    override fun en(id: Id<KontaktniObrazec>) = this.db.get(id = id)
    override fun shrani(kontaktni_obrazec: KontaktniObrazec) = this.db.save(entiteta = kontaktni_obrazec)
}
