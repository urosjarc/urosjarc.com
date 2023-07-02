package si.urosjarc.server.core.services

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.MongoClient
import si.urosjarc.server.core.domain.Entiteta


class DbService(val db_url: String, val db_name: String) {
    private val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()
    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(db_url)).serverApi(serverApi).build()
    private val mongoClient = MongoClient.create(settings)
    val db = mongoClient.getDatabase(db_name)

    fun nafilaj() {
        Entiteta::class.sealedSubclasses.forEach {
            db.createCollection(collectionName = it.simpleName.toString())
        }
    }

    fun sprazni() = db.listCollectionNames()
        .forEach { db.getCollection<Any>(collectionName = it).drop() }

    inline fun <reified T : Entiteta<out T>> ustvari(entity: T) {
        db.getCollection<T>(collectionName = entity::class.simpleName.toString()).insertOne(entity).also {
            entity.id = it.insertedId?.asObjectId()?.value
        }
    }

    inline fun <reified T : Entiteta<out T>> dobi(): List<T> {
        return db.getCollection<T>(collectionName = T::class.simpleName.toString()).find().toList()
    }
}
