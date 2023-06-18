package si.programerski_klub.server.app.repos

import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.core.domain.napredovanje.Test
import si.programerski_klub.server.core.repos.TestiRepo

class TestiDbMongoRepo(val db: DbMongo) : TestiRepo {

    override fun shrani(test: Test) = this.db.save(entiteta = test)

}
