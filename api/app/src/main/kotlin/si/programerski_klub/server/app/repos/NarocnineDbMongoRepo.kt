package si.programerski_klub.server.app.repos

import org.litote.kmongo.Id
import org.litote.kmongo.find
import org.litote.kmongo.findOne
import si.programerski_klub.server.app.services.AND
import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.app.services.OR
import si.programerski_klub.server.app.services.str
import si.programerski_klub.server.core.domain.placevanje.Narocnina
import si.programerski_klub.server.core.domain.placevanje.Produkt
import si.programerski_klub.server.core.domain.uprava.Oseba
import si.programerski_klub.server.core.repos.DbRezultatIskanja
import si.programerski_klub.server.core.repos.NarocnineRepo

class NarocnineDbMongoRepo(val db: DbMongo) : NarocnineRepo {
    override fun shrani(narocnine: Narocnina) = this.db.save(entiteta = narocnine)
    override fun ena(id: Id<Oseba>, tip: Produkt.Tip): DbRezultatIskanja<Narocnina> {
        val query0 = """{"${Narocnina::id_uporabnika.name}\": ${id.str()}}"""
        val query1 = """{"${Narocnina::produkt.name}.${Produkt::tip.name}\": \"$tip\"}"""
        val query2 = """{$AND: [$query0, $query1]}"""
        return when (val r = this.db.col<Narocnina>().findOne(query2)) {
            null -> DbRezultatIskanja.PASS()
            else -> DbRezultatIskanja.DATA(data = r)
        }
    }

    override fun ena(id: Id<Narocnina>) = this.db.get(id = id)
    override fun vse(id: Id<Oseba>): List<Narocnina> {
        val query0 = "{\"${Narocnina::id_uporabnika.name}\": ${id.str()}}"
        val query1 = "{\"${Narocnina::id_placnika.name}\": ${id.str()}}"
        val query2 = "{$OR: [$query0, $query1]}"
        return this.db.col<Narocnina>().find(query2).toList()
    }
}
