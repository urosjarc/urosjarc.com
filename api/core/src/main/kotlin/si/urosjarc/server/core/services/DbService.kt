package si.urosjarc.server.core.services

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.MongoClient
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.extend.ime


class DbService(val db_url: String, val db_name: String) {
    private val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()
    private val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(db_url)).serverApi(serverApi).build()
    private val mongoClient = MongoClient.create(settings)
    val db = mongoClient.getDatabase(db_name)

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

                val tematika = Entiteta.nakljucni<Tematika>().apply { this.zvezek_id.value = zvezek.id.value }
                all_tematika.add(tematika)

                val kontakt = Entiteta.nakljucni<Kontakt>().apply { this.oseba_id.value = oseba.id.value }
                all_kontakt.add(kontakt)

                val naslov = Entiteta.nakljucni<Naslov>().apply { this.oseba_id.value = oseba.id.value }
                all_naslov.add(naslov)

                val ucenje0 = Entiteta.nakljucni<Ucenje>().apply {
                    this.ucenec_id.value = oseba.id.value; this.ucitelj_id.value = all_oseba.random().id.value
                }; all_ucenje.add(ucenje0)

                val ucenje1 = Entiteta.nakljucni<Ucenje>().apply {
                    this.ucitelj_id.value = oseba.id.value; this.ucenec_id.value = all_oseba.random().id.value
                }; all_ucenje.add(ucenje1)

                val test = Entiteta.nakljucni<Test>().apply { this.oseba_id.value = oseba.id.value }
                all_test.add(test)

                (1..5).forEach {
                    val sporocilo = Entiteta.nakljucni<Sporocilo>().apply {
                        this.posiljatelj_id.value = all_oseba.random().id.value
                    }
                    all_sporocilo.add(sporocilo)

                    val naloga = Entiteta.nakljucni<Naloga>().apply { this.tematika_id.value = tematika.id.value }
                    all_naloga.add(naloga)

                    (1..5).forEach {
                        val status = Entiteta.nakljucni<Status>().apply {
                            this.naloga_id.value = naloga.id.value
                            this.test_id.value = test.id.value
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

    inline fun <reified T : Entiteta<out T>> ustvari(entiteta: T) {
        db.getCollection<T>(collectionName = ime<T>()).insertOne(entiteta).also {
            entiteta.id.value = it.insertedId?.asObjectId()?.value
        }
    }

    inline fun <reified T : Entiteta<out T>> ustvari(entitete: Collection<T>) {
        println(ime<T>())
        db.getCollection<T>(collectionName = ime<T>())
            .insertMany(documents = entitete as List<T>)
    }

    inline fun <reified T : Entiteta<out T>> dobi(): List<T> {
        return db.getCollection<T>(collectionName = ime<T>()).find().toList()
    }
}
