package si.programerski_klub.server.app.repos

import org.litote.kmongo.Id
import org.litote.kmongo.eq
import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.core.domain.placevanje.Ponudba
import si.programerski_klub.server.core.domain.placevanje.Produkt
import si.programerski_klub.server.core.repos.DbRezultatId
import si.programerski_klub.server.core.repos.ProduktiRepo

class ProduktDbMongoRepo(val db: DbMongo) : ProduktiRepo {
    override fun en(id: Id<Produkt>) = this.db.get(id = id)
    override fun vsi(id: Id<Ponudba>): DbRezultatId<List<Produkt>> {
        return when (val r = this.db.get(id = id)) {
            is DbRezultatId.ERROR -> DbRezultatId.ERROR()
            is DbRezultatId.DATA -> this.db.get_many(ids = r.data.produkti)
        }
    }

    override fun vsi(tip: Produkt.Tip): List<Produkt> {
        return this.db.col<Produkt>().find(Produkt::tip eq Produkt.Tip.CLANARINA).toList()
    }

    override fun shrani(produkt: Produkt) = this.db.save(entiteta = produkt)
}
