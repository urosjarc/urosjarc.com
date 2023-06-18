package si.programerski_klub.server.app.repos

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.*
import si.programerski_klub.server.app.services.DbMongo
import si.programerski_klub.server.core.base.UnikatnaEntiteta
import si.programerski_klub.server.core.domain.napredovanje.Postaja
import si.programerski_klub.server.core.extend.to_snake_case
import si.programerski_klub.server.core.repos.*

class PostajeDbMongoRepo(val db: DbMongo) : PostajeRepo {
    override fun shrani(postaja: Postaja): DbRezultatShranitve<Postaja> = this.db.save(entiteta = postaja)
    override fun zacetne(): List<Postaja> = this.db.col<Postaja>().find(Postaja::stars eq null).toList()
    override fun dedici(id: Id<Postaja>): DbRezultatIskanja<List<Postaja>> {

        @Serializable
        data class Result(
            @Contextual
            @SerialName("_id")
            val id: Id<Postaja>,
            val dedici: List<Postaja> = emptyList()
        )

        val results = this.db.col<Postaja>().aggregate(
            listOf(
                match(UnikatnaEntiteta<Postaja>::id eq id),
                graphLookup(
                    from = Postaja::class.simpleName.toString().to_snake_case(),
                    startWith = "\$_id",
                    connectFromField = Postaja::otroci.name,
                    connectToField = "_id",
                    fieldAs = Result::dedici.name
                )
            ),
            Result::class.java
        ).toList()

        return when (val r = results.firstOrNull()) {
            null -> DbRezultatIskanja.PASS()
            else -> DbRezultatIskanja.DATA(data = r.dedici)
        }
    }

    override fun shrani(postaje: List<Postaja>): DbRezultatShranitve<List<Postaja>> {
        val data = mutableListOf<Postaja>()
        for (postaja in postaje) {
            when (val r = this.db.save(postaja)) {
                is DbRezultatShranitve.DATA -> data.add(r.data)
                is DbRezultatShranitve.FATAL_DB_NAPAKA -> return DbRezultatShranitve.FATAL_DB_NAPAKA()
            }
        }
        return DbRezultatShranitve.DATA(data)
    }

    override fun izbrisi(ids: List<Id<Postaja>>): DbRezultatIzbrisa {
        val result = this.db.col<Postaja>().deleteMany(UnikatnaEntiteta<Postaja>::id `in` ids)
        return when (ids.size.toLong() == result.deletedCount) {
            true -> DbRezultatIzbrisa.PASS
            false -> DbRezultatIzbrisa.WARN_DELNI_IZBRIS
        }
    }

    override fun vse(ids: Set<Id<Postaja>>): DbRezultatId<List<Postaja>> = this.db.get_many(ids = ids)
    override fun ena(id: Id<Postaja>): DbRezultatId<Postaja> = this.db.get(id = id)
    override fun naslednje(id: Id<Postaja>): DbRezultatId<List<Postaja>> {
        return when (val r = this.db.get(id = id)) {
            is DbRezultatId.DATA -> return when (val r_otroci = this.db.get_many(r.data.otroci)) {
                is DbRezultatId.DATA -> {
                    DbRezultatId.DATA(data = r_otroci.data)
                }

                is DbRezultatId.ERROR -> DbRezultatId.ERROR()
            }

            is DbRezultatId.ERROR -> DbRezultatId.ERROR()
        }
    }
}
