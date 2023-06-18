package si.programerski_klub.server.app.repos

import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.core.domain.placevanje.Ponudba
import si.programerski_klub.server.core.repos.PonudbeRepo

class PonudbeDbMongoRepo(val db: DbMongo) : PonudbeRepo {
    override fun vse(): List<Ponudba> = this.db.get_all()
    override fun shrani(ponudba: Ponudba) = this.db.save(entiteta = ponudba)
}
