package services

import base.AnyId
import base.Id
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.model.*
import com.mongodb.kotlin.client.AggregateIterable
import com.mongodb.kotlin.client.MongoClient
import data.OsebaData
import domain.*
import extend.*
import io.github.serpro69.kfaker.Faker
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.apache.logging.log4j.kotlin.logger
import org.bson.codecs.configuration.CodecRegistries
import org.bson.conversions.Bson
import serialization.IdCodec
import kotlin.random.Random
import kotlin.reflect.KProperty1
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


val faker = Faker()
var counters = mutableMapOf<String, Int>()

class Filters {
    companion object {
        /**
         * LOGIC
         */
        fun AND(vararg filters: Bson): Bson {
            return com.mongodb.client.model.Filters.and(listOf(*filters))
        }

        fun OR(vararg filters: Bson): Bson {
            return com.mongodb.client.model.Filters.or(listOf(*filters))
        }

        fun <T> EQ(id: Id<T>): Bson {
            return com.mongodb.client.model.Filters.eq(id.value)
        }

        fun <T> EQ(prop: KProperty1<*, T>, value: T): Bson {
            return com.mongodb.client.model.Filters.eq(prop.name, value)
        }

        fun <T> CONTAINS(prop: KProperty1<*, Set<T>>, value: T): Bson {
            return com.mongodb.client.model.Filters.eq(prop.name, value)
        }

    }

}


class DbService(val db_url: String, val db_name: String) {

    val idCodecRegistry = CodecRegistries.fromCodecs(IdCodec())
    var codecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        idCodecRegistry
    )


    private val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()
    private val settings =
        MongoClientSettings.builder().applyConnectionString(ConnectionString(db_url)).serverApi(serverApi).build()
    private val mongoClient = MongoClient.create(settings)

    val log = this.logger()
    val db = mongoClient.getDatabase(db_name).withCodecRegistry(codecRegistry)
    val audits = db.getCollection<Audit>(collectionName = ime<Audit>())
    val osebe = db.getCollection<Oseba>(collectionName = ime<Oseba>())
    val statusi = db.getCollection<Status>(collectionName = ime<Status>())
    val testi = db.getCollection<Test>(collectionName = ime<Test>())
    val napake = db.getCollection<Napaka>(collectionName = ime<Napaka>())
    val kontakti = db.getCollection<Kontakt>(collectionName = ime<Kontakt>())

    inline fun <reified T : Any> nakljucni(): T {
        val obj = faker.randomProvider.randomClassInstance<T> {
            this.typeGenerator<MutableSet<T>> { mutableSetOf() }
            this.typeGenerator<LocalDate> { LocalDate.danes(dDni = Random.nextInt(3, 20)) }
            this.typeGenerator<LocalDateTime> { LocalDateTime.zdaj() }
            this.typeGenerator<String> { pInfo ->
                val value = counters.getOrDefault(pInfo.name, -1) + 1
                counters[pInfo.name] = value
                "${pInfo.name}_${value}"
            }
        }
        return obj
    }

    fun nafilaj_NUJNO() {
        val oseba = Oseba(
            ime = "Uroš",
            priimek = "Jarc",
            username = "urosjarc",
            tip = mutableSetOf(Oseba.Tip.SERVER, Oseba.Tip.ADMIN)
        )
        val telefon = Kontakt(oseba_id = mutableSetOf(oseba._id), data = "Uros Jarc", tip = Kontakt.Tip.TELEFON)
        val email = Kontakt(oseba_id = mutableSetOf(oseba._id), data = "info@urosjarc.com", tip = Kontakt.Tip.EMAIL)

        this.ustvari(telefon)
        this.ustvari(oseba)
        this.ustvari(email)
    }

    fun nafilaj() {
        val all_napaka = mutableListOf<Napaka>()
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
        val all_audit = mutableListOf<Audit>()

        this.nafilaj_NUJNO()

        (1..5).forEach {

            val oseba = nakljucni<Oseba>()
            all_oseba.add(oseba)

            val zvezek = nakljucni<Zvezek>()
            all_zvezek.add(zvezek)

            (1..3).forEach {
                val test = nakljucni<Test>().apply { this.oseba_id = oseba._id }
                all_test.add(test)

                val napaka = nakljucni<Napaka>().apply { this.entitete_id = setOf(oseba._id.vAnyId()) }
                all_napaka.add(napaka)
            }

            (1..2).forEach {

                val tematika = nakljucni<Tematika>().apply { this.zvezek_id = zvezek._id }
                all_tematika.add(tematika)

                val kontakt = nakljucni<Kontakt>().apply { this.oseba_id = mutableSetOf(oseba._id) }
                all_kontakt.add(kontakt)

                val naslov = nakljucni<Naslov>().apply { this.oseba_id = oseba._id }
                all_naslov.add(naslov)

                val ucenje0 = nakljucni<Ucenje>().apply {
                    this.oseba_ucenec_id = oseba._id;
                    this.oseba_ucitelj_id = all_oseba.random()._id
                }; all_ucenje.add(ucenje0)

                val ucenje1 = nakljucni<Ucenje>().apply {
                    this.oseba_ucitelj_id = oseba._id
                    this.oseba_ucenec_id = all_oseba.random()._id
                }; all_ucenje.add(ucenje1)


                (1..5).forEach {
                    val sporocilo = nakljucni<Sporocilo>().apply {
                        this.kontakt_posiljatelj_id = all_kontakt.random()._id
                        this.kontakt_prejemnik_id = mutableSetOf(all_kontakt.random()._id)
                    }
                    all_sporocilo.add(sporocilo)

                    val naloga = nakljucni<Naloga>().apply { this.tematika_id = tematika._id }
                    all_naloga.add(naloga)

                    (1..15).forEach {
                        val status = nakljucni<Status>().apply {
                            this.naloga_id = all_naloga.random()._id
                            this.test_id = all_test.random()._id
                        }
                        all_status.add(status)

                        (1..Random.nextInt(0, 5)).forEach {
                            val audit = Audit(
                                entitete_id = setOf(oseba._id.vAnyId(), status.test_id.vAnyId(), status._id.vAnyId()),
                                tip = Audit.Tip.STATUS_TIP_POSODOBITEV,
                                opis = Status.Tip.values().random().name,
                                entiteta = "entiteta",
                                ustvarjeno = LocalDateTime.zdaj(dDni = -Random.nextInt(0, 20)),
                                trajanje = Random.nextInt(2, 20).toDuration(unit = DurationUnit.MINUTES)
                            )
                            all_audit.add(audit)

                        }

                    }

                }

            }
        }
        this.ustvari(all_oseba)
        this.ustvari(all_napaka)
        this.ustvari(all_kontakt)
        this.ustvari(all_sporocilo)
        this.ustvari(all_naslov)
        this.ustvari(all_ucenje)
        this.ustvari(all_test)
        this.ustvari(all_status)
        this.ustvari(all_naloga)
        this.ustvari(all_tematika)
        this.ustvari(all_zvezek)
        this.ustvari(all_audit)

    }

    fun sprazni() = db.listCollectionNames().forEach { db.getCollection<Any>(collectionName = it).drop() }

    inline fun <reified T : Entiteta<T>> ustvari(entiteta: T): Boolean {
        return db.getCollection<T>(collectionName = ime<T>()).insertOne(entiteta).wasAcknowledged()
    }

    inline fun <reified T : Entiteta<T>> ustvari(entitete: Collection<T>): Boolean {
        return db.getCollection<T>(collectionName = ime<T>()).insertMany(documents = entitete as List<T>)
            .wasAcknowledged()
    }

    inline fun <reified T : Entiteta<T>> dobi(stran: Int): List<T> {
        return db.getCollection<T>(collectionName = ime<T>()).find().stran(n = stran).toList()
    }

    inline fun <reified T : Entiteta<T>> dobi(_id: Id<T>): T? {
        return db.getCollection<T>(collectionName = ime<T>()).find(Filters.EQ(_id)).firstOrNull()
    }

    inline fun <reified T : Entiteta<T>> posodobi(entiteta: T): T? {
        return db.getCollection<T>(collectionName = ime<T>()).findOneAndReplace(Filters.EQ(entiteta._id), entiteta)
    }

    inline fun <reified T : Entiteta<T>> odstrani(_id: Id<T>): Boolean {
        return db.getCollection<T>(collectionName = ime<T>()).deleteOne(Filters.EQ(_id)).wasAcknowledged()
    }

    fun audits(entity_id: AnyId, stran: Int?): List<Audit> {
        val audits = audits.find(
            filter = Filters.CONTAINS(Audit::entitete_id, entity_id)
        ).sort(Sorts.descending(Audit::ustvarjeno.name))

        return when (stran) {
            null -> audits
            else -> audits.stran(n = stran)
        }.toList()
    }

    fun napake(entity_id: AnyId, stran: Int?): List<Napaka> {
        val napake = napake.find(
            filter = Filters.CONTAINS(Napaka::entitete_id, entity_id)
        ).sort(Sorts.descending(Napaka::ustvarjeno.name))
        return when (stran) {
            null -> napake
            else -> napake.stran(n = stran)
        }.toList()
    }


    fun profil(id: Id<Oseba>): OsebaData {
        val aggregation: AggregateIterable<OsebaData> = osebe.aggregate<OsebaData>(
            listOf(
                Aggregates.match(Filters.EQ(id)),
                Aggregates_project_root(Oseba::class),
                Aggregates_lookup(
                    from = Naslov::oseba_id, to = Oseba::_id
                ),
                Aggregates_lookup(
                    from = Test::oseba_id,
                    to = Oseba::_id,
                    pipeline = listOf(
                        Aggregates_lookup(
                            from = Status::test_id,
                            to = Test::_id,
                            pipeline = listOf(
                                Aggregates_lookup(
                                    from = Naloga::_id,
                                    to = Status::naloga_id,
                                    pipeline = listOf(
                                        Aggregates_lookup(
                                            from = Tematika::_id,
                                            to = Naloga::tematika_id,
                                        ),
                                    ),
                                ),
                            ),
                        ),
                    ),
                ),
                Aggregates_lookup(
                    from = Kontakt::oseba_id, to = Oseba::_id, pipeline = listOf(
                        Aggregates_lookup(
                            from = Sporocilo::kontakt_prejemnik_id, to = Kontakt::_id, pipeline = listOf(
                                Aggregates_lookup(
                                    from = Kontakt::_id, to = Sporocilo::kontakt_posiljatelj_id, pipeline = listOf(
                                        Aggregates_lookup(
                                            from = Oseba::_id, to = Kontakt::oseba_id
                                        ),
                                    )
                                ),
                            )
                        ),
                        Aggregates_lookup(
                            from = Sporocilo::kontakt_posiljatelj_id, to = Kontakt::_id, pipeline = listOf(
                                Aggregates_lookup(
                                    from = Kontakt::_id, to = Sporocilo::kontakt_prejemnik_id, pipeline = listOf(
                                        Aggregates_lookup(
                                            from = Oseba::_id, to = Kontakt::oseba_id
                                        ),
                                    )
                                ),
                            )
                        ),
                    )
                ),
                Aggregates_lookup(
                    from = Ucenje::oseba_ucenec_id, to = Ucenje::_id, pipeline = listOf(
                        Aggregates_lookup(
                            from = Oseba::_id,
                            to = Ucenje::oseba_ucitelj_id,
                        )
                    )
                ),
                Aggregates_lookup(
                    from = Ucenje::oseba_ucitelj_id, to = Oseba::_id, pipeline = listOf(
                        Aggregates_lookup(
                            from = Oseba::_id, to = Ucenje::oseba_ucenec_id
                        )
                    )
                ),
            )
        )

        return aggregation.first()
    }

    fun oseba_najdi(ime: String, priimek: String, telefon: String, email: String): OsebaData? {
        val aggregation: AggregateIterable<OsebaData> = osebe.aggregate<OsebaData>(
            listOf(
                Aggregates.match(
                    Filters.AND(
                        Filters.EQ(Oseba::ime, ime),
                        Filters.EQ(Oseba::priimek, priimek),
                    )
                ), Aggregates_project_root(Oseba::class), Aggregates_lookup(
                    from = Kontakt::oseba_id,
                    to = Oseba::_id,
                    pipeline = listOf(
                        Aggregates.match(
                            Filters.OR(
                                Filters.EQ(Kontakt::data, telefon),
                                Filters.EQ(Kontakt::data, email),
                            )
                        ),
                    ),
                )
            )
        )
        return aggregation.firstOrNull()
    }

    fun oseba_najdi(tip: Oseba.Tip): List<OsebaData> {
        val aggregation: AggregateIterable<OsebaData> = osebe.aggregate<OsebaData>(
            listOf(
                Aggregates.match(
                    Filters.CONTAINS(Oseba::tip, tip),
                ),
                Aggregates_project_root(Oseba::class),
                Aggregates_lookup(
                    from = Kontakt::oseba_id,
                    to = Oseba::_id,
                    pipeline = listOf()
                )
            )
        )
        aggregation.explain_aggregation()
        return aggregation.toList()
    }

    fun kontakt_najdi(data: String): Kontakt? = kontakti.find(Filters.EQ(Kontakt::data, data)).firstOrNull()

    /**
     * Zaradi tega ker se mora preveriti ali je uporabnik owner statusa!
     */
    fun status_obstaja(id: Id<Status>, oseba_id: Id<Oseba>, test_id: Id<Test>): Boolean {
        val aggregation: AggregateIterable<OsebaData> = osebe.aggregate<OsebaData>(
            listOf(
                Aggregates.match(Filters.EQ(Oseba::_id, oseba_id)),
                Aggregates_project_root(Oseba::class),
                Aggregates_lookup(
                    from = Test::oseba_id,
                    to = Oseba::_id,
                    pipeline = listOf(
                        Aggregates.match(Filters.EQ(Test::_id, test_id)),
                        Aggregates_lookup(
                            from = Status::test_id, to = Test::_id, pipeline = listOf(
                                Aggregates.match(Filters.EQ(Status::_id, id)),
                            )
                        ),
                    ),
                ),
            )
        )

        return aggregation.firstOrNull()?.test_refs?.get(0)?.status_refs?.get(0)?.status?._id == id
    }

    fun status_update(id: Id<Status>, oseba_id: Id<Oseba>, test_id: Id<Test>, tip: Status.Tip, sekund: Int): Status? {
        val r: Status = statusi.findOneAndUpdate(
            filter = Filters.AND(
                Filters.EQ(id),
                Filters.EQ(Status::test_id, test_id)
            ),
            update = Updates.set(Status::tip.name, tip),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        ) ?: return null

        val audit = Audit(
            entiteta = ime<Status>(),
            tip = Audit.Tip.STATUS_TIP_POSODOBITEV,
            trajanje = sekund.toDuration(DurationUnit.MINUTES),
            opis = r.tip.name,
            entitete_id = setOf(id.vAnyId(), oseba_id.vAnyId(), test_id.vAnyId())
        )

        this.ustvari(audit)

        return r
    }

    fun test_update(id: Id<Test>, oseba_id: Id<Oseba>, datum: LocalDate): Test? {
        val r: Test = testi.findOneAndUpdate(
            filter = Filters.AND(
                Filters.EQ(Test::_id, id),
                Filters.EQ(Test::oseba_id, oseba_id),
            ),
            update = Updates.set(Test::deadline.name, datum),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        ) ?: return null

        val audit = Audit(
            entiteta = ime<Test>(),
            tip = Audit.Tip.TEST_DATUM_POSODOBITEV,
            trajanje = Duration.ZERO,
            opis = r.deadline.toString(),
            entitete_id = setOf(id.vAnyId(), oseba_id.vAnyId())
        )

        this.ustvari(audit)

        return r
    }

}
