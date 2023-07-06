package app.services

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.MongoClient
import core.domain.*
import io.github.serpro69.kfaker.Faker
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import app.extend.danes
import app.extend.ime
import app.extend.zdaj
import app.repos.OsebaRepo
import si.urosjarc.server.core.domain.*

val faker = Faker()
var counters = mutableMapOf<String, Int>()

class DbService(val db_url: String, val db_name: String) {
    private val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()
    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(db_url)).serverApi(serverApi).build()
    private val mongoClient = MongoClient.create(settings)

    val db = mongoClient.getDatabase(db_name)
    val osebaRepo = OsebaRepo(collection = db.getCollection(collectionName = ime<Oseba>()))

    inline fun <reified T : Any> nakljucni(): T {
        val obj = faker.randomProvider.randomClassInstance<T> {
            this.typeGenerator<MutableSet<T>> { mutableSetOf() }
            this.typeGenerator<LocalDate> { LocalDate.danes() }
            this.typeGenerator<LocalDateTime> { LocalDateTime.zdaj() }
            this.typeGenerator<String> { pInfo ->
                val value = counters.getOrDefault(pInfo.name, -1) + 1
                counters[pInfo.name] = value
                "${pInfo.name}_${value}"
            }
        }
        return obj
    }


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

            val oseba = nakljucni<Oseba>()
            all_oseba.add(oseba)

            val zvezek = nakljucni<Zvezek>()
            all_zvezek.add(zvezek)

            (1..2).forEach {

                val tematika = nakljucni<Tematika>().apply { this.zvezek_id = zvezek._id }
                all_tematika.add(tematika)

                val kontakt = nakljucni<Kontakt>().apply { this.oseba_id = oseba._id }
                all_kontakt.add(kontakt)

                val naslov = nakljucni<Naslov>().apply { this.oseba_id = oseba._id }
                all_naslov.add(naslov)

                val ucenje0 = nakljucni<Ucenje>().apply {
                    this.oseba_ucenec_id = oseba._id; this.oseba_ucitelj_id = all_oseba.random()._id
                }; all_ucenje.add(ucenje0)

                val ucenje1 = nakljucni<Ucenje>().apply {
                    this.oseba_ucitelj_id = oseba._id; this.oseba_ucenec_id = all_oseba.random()._id
                }; all_ucenje.add(ucenje1)

                val test = nakljucni<Test>().apply { this.oseba_id = oseba._id }
                all_test.add(test)

                (1..5).forEach {
                    val sporocilo = nakljucni<Sporocilo>().apply {
                        this.kontakt_posiljatelj_id = all_kontakt.random()._id
                        this.kontakt_prejemnik_id = all_kontakt.random()._id
                    }
                    all_sporocilo.add(sporocilo)

                    val naloga = nakljucni<Naloga>().apply { this.tematika_id = tematika._id }
                    all_naloga.add(naloga)

                    (1..5).forEach {
                        val status = nakljucni<Status>().apply {
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
            .insertOne(entiteta).also { entiteta._id = it.insertedId?.asObjectId()?.value.toString() }
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

    fun filter_one(_id: String?) = Filters.eq("_id", _id)

    inline fun <reified T : Entiteta> dobi(_id: String): T? {
        return db.getCollection<T>(collectionName = ime<T>())
            .find(filter_one(_id))
            .firstOrNull()
    }

    inline fun <reified T : Entiteta> posodobi(entiteta: T): T? {
        if (entiteta._id == null) return null
        return db.getCollection<T>(collectionName = ime<T>())
            .findOneAndReplace(filter_one(entiteta._id), entiteta)
    }

    inline fun <reified T : Entiteta> odstrani(_id: String): Boolean {
        return db.getCollection<T>(collectionName = ime<T>())
            .deleteOne(filter_one(_id))
            .wasAcknowledged()
    }
}
