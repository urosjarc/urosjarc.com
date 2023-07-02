package si.urosjarc.server.core.services

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.MongoClient
import org.bson.types.ObjectId
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.OsebaRepo


class DbService(val db_url: String, val db_name: String) {
    private val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()
    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(db_url)).serverApi(serverApi).build()
    private val mongoClient = MongoClient.create(settings)
    val db = mongoClient.getDatabase(db_name)

    val osebaRepo = OsebaRepo(collection = db.getCollection(collectionName = ime<Oseba>()))

    fun nafilaj() {
        val all_oseba = mutableListOf<Oseba>()
        val all_kontakt = mutableListOf<Kontakt>()
        val all_sporocilo = mutableListOf<Sporocilo>()
        val all_naslov = mutableListOf<Naslov>()
        val all_ucenje = mutableListOf<Ucenje>()
        val all_test = mutableListOf<Test>()
        val all_status = mutableListOf<Status>()
        val all_naloga = mutableListOf<Naloga>()
        val all_tematika = mutableListOf<Tematika>()
        val all_zvezek = mutableListOf<Zvezek>()

        (1..5).forEach {

            val oseba = Entiteta.nakljucni<Oseba>()
            all_oseba.add(oseba)

            val zvezek = Entiteta.nakljucni<Zvezek>()
            all_zvezek.add(zvezek)

            (1..2).forEach {

                val tematika = Entiteta.nakljucni<Tematika>().apply { this.zvezek_id = zvezek._id }
                all_tematika.add(tematika)

                val kontakt = Entiteta.nakljucni<Kontakt>().apply { this.oseba_id = oseba._id }
                all_kontakt.add(kontakt)

                val naslov = Entiteta.nakljucni<Naslov>().apply { this.oseba_id = oseba._id }
                all_naslov.add(naslov)

                val ucenje0 = Entiteta.nakljucni<Ucenje>().apply {
                    this.oseba_ucenec_id = oseba._id; this.oseba_ucitelj_id = all_oseba.random()._id
                }; all_ucenje.add(ucenje0)

                val ucenje1 = Entiteta.nakljucni<Ucenje>().apply {
                    this.oseba_ucitelj_id = oseba._id; this.oseba_ucenec_id = all_oseba.random()._id
                }; all_ucenje.add(ucenje1)

                val test = Entiteta.nakljucni<Test>().apply { this.oseba_id = oseba._id }
                all_test.add(test)

                (1..5).forEach {
                    val sporocilo = Entiteta.nakljucni<Sporocilo>().apply {
                        this.oseba_posiljatelj_id = all_oseba.random()._id
                    }
                    all_sporocilo.add(sporocilo)

                    val naloga = Entiteta.nakljucni<Naloga>().apply { this.tematika_id = tematika._id }
                    all_naloga.add(naloga)

                    (1..5).forEach {
                        val status = Entiteta.nakljucni<Status>().apply {
                            this.naloga_id = naloga._id
                            this.test_id = test._id
                        }
                        all_status.add(status)
                    }

                }

            }
        }
        this.ustvari(all_oseba)
        this.ustvari(all_kontakt)
        this.ustvari(all_sporocilo)
        this.ustvari(all_naslov)
        this.ustvari(all_ucenje)
        this.ustvari(all_test)
        this.ustvari(all_status)
        this.ustvari(all_naloga)
        this.ustvari(all_tematika)
        this.ustvari(all_zvezek)

    }

    fun sprazni() = db.listCollectionNames()
        .forEach { db.getCollection<Any>(collectionName = it).drop() }

    inline fun <reified T : Entiteta> ustvari(entiteta: T): Boolean {
        return db.getCollection<T>(collectionName = ime<T>())
            .insertOne(entiteta).also { entiteta._id = it.insertedId?.asObjectId()?.value }
            .wasAcknowledged()
    }

    inline fun <reified T : Entiteta> ustvari(entitete: Collection<T>): Boolean {
        return db.getCollection<T>(collectionName = ime<T>())
            .insertMany(documents = entitete as List<T>)
            .wasAcknowledged()
    }

    inline fun <reified T : Entiteta> dobi(stran: Int): List<T> {
        return db.getCollection<T>(collectionName = ime<T>())
            .find()
            .skip(stran * 100)
            .limit(100)
            .toList()
    }

    fun filter_one(_id: ObjectId?) = Filters.eq("_id", _id)

    inline fun <reified T : Entiteta> dobi(_id: ObjectId): T? {
        return db.getCollection<T>(collectionName = ime<T>())
            .find(filter_one(_id))
            .firstOrNull()
    }

    inline fun <reified T : Entiteta> posodobi(entiteta: T): T? {
        if (entiteta._id == null) return null
        return db.getCollection<T>(collectionName = ime<T>())
            .findOneAndReplace(filter_one(entiteta._id), entiteta)
    }

    inline fun <reified T : Entiteta> odstrani(_id: ObjectId): Boolean {
        return db.getCollection<T>(collectionName = ime<T>())
            .deleteOne(filter_one(_id))
            .wasAcknowledged()
    }
}
