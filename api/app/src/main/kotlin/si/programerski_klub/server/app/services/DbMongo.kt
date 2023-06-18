package si.programerski_klub.server.app.services

import com.mongodb.MongoException
import com.mongodb.MongoWriteConcernException
import com.mongodb.MongoWriteException
import com.mongodb.client.MongoCollection
import org.litote.kmongo.*
import org.litote.kmongo.util.KMongoUtil
import si.programerski_klub.server.app.repos.*
import si.programerski_klub.server.core.base.UnikatnaEntiteta
import si.programerski_klub.server.core.extend.to_snake_case
import si.programerski_klub.server.core.repos.DbRezultatId
import si.programerski_klub.server.core.repos.DbRezultatIzbrisa
import si.programerski_klub.server.core.repos.DbRezultatShranitve
import si.programerski_klub.server.core.services.DbService

fun <T> Id<T>.str(): String = """ObjectId("$this")"""
fun <T> Set<Id<T>>.str(): String = "[" + this.joinToString(", ") { it.str() } + "]"
const val IN = "\"\$in\""
const val AND = "\"\$and\""
const val OR = "\"\$or\""
const val ID = "\"_id\""

class DbMongo(connection: String, database: String, val allow_db_drop: Boolean = false) : DbService {
    private val client = KMongo.createClient(connection)
    private val mdb = this.client.getDatabase(database)

    override val testi = TestiDbMongoRepo(this)
    override val naloge = NalogeDbMongoRepo(this)
    override val narocila = NarocilaDbMongoRepo(this)
    override val narocnine = NarocnineDbMongoRepo(this)
    override val osebe = OsebeDbMongoRepo(this)
    override val ponudbe = PonudbeDbMongoRepo(this)
    override val postaje = PostajeDbMongoRepo(this)
    override val produkti = ProduktDbMongoRepo(this)
    override val programi = ProgramDbMongoRepo(this)
    override val kontaktni_obrazec = KontaktniObrazecDbMongoRepo(this)

    internal inline fun <reified T : UnikatnaEntiteta<T>> col(): MongoCollection<T> {
        val name = T::class.simpleName
        if (name != null) {
            return this.mdb.getCollection(name.to_snake_case(), T::class.java)
        } else return this.mdb.getCollection(KMongoUtil.defaultCollectionName(T::class), T::class.java)
    }

    internal inline fun <reified T : UnikatnaEntiteta<T>> save(entiteta: T): DbRezultatShranitve<T> {
        return try {
            this.col<T>().save(entiteta)
            DbRezultatShranitve.DATA(data = entiteta)
        } catch (err: MongoWriteConcernException) {
            DbRezultatShranitve.FATAL_DB_NAPAKA()
        } catch (err: MongoWriteException) {
            DbRezultatShranitve.FATAL_DB_NAPAKA()
        } catch (err: MongoException) {
            DbRezultatShranitve.FATAL_DB_NAPAKA()
        }
    }

    internal inline fun <reified T : UnikatnaEntiteta<T>> get_all() = this.col<T>().find().toList()

    internal inline fun <reified T : UnikatnaEntiteta<T>> get_many(ids: Set<Id<T>>): DbRezultatId<List<T>> {
        val query = """{$ID: {$IN: ${ids.str()}}}"""
        val data: List<T> = this.col<T>().find(query).toList()
        return when (data.size == ids.size) {
            true -> DbRezultatId.DATA(data = data)
            false -> DbRezultatId.ERROR()
        }
    }

    internal inline fun <reified T : UnikatnaEntiteta<T>> get(id: Id<T>): DbRezultatId<T> {
        val query2 = """{$ID: ${id.str()}}"""
        return when (val r = this.col<T>().findOne(query2)) {
            null -> DbRezultatId.ERROR()
            else -> DbRezultatId.DATA(data = r)
        }
    }

    /**
     * ADMIN
     */
    override fun izbrisi_vse(): DbRezultatIzbrisa {
        if (this.allow_db_drop) {
            this.mdb.drop()
            return DbRezultatIzbrisa.PASS
        }
        return DbRezultatIzbrisa.ERROR_DB_IZBRIS_NI_DOVOLJEN
    }


}
