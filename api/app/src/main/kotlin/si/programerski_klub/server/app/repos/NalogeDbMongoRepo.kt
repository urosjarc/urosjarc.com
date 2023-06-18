package si.programerski_klub.server.app.repos

import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.core.domain.napredovanje.Naloga
import si.programerski_klub.server.core.repos.NalogeRepo

class NalogeDbMongoRepo(val db: DbMongo) : NalogeRepo {
    override fun shrani(naloga: Naloga) = this.db.save(entiteta = naloga)
}
