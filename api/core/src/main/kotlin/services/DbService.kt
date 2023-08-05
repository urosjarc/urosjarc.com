package services

import base.AnyId
import base.Encrypted
import base.Id
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.model.*
import com.mongodb.kotlin.client.AggregateIterable
import com.mongodb.kotlin.client.MongoClient
import data.OsebaData
import data.UcenecData
import domain.*
import extend.*
import kotlinx.datetime.LocalDate
import org.apache.logging.log4j.kotlin.logger
import org.bson.conversions.Bson
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


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

        fun <T> EQ(prop: KProperty1<*, Id<T>>, value: Id<T>): Bson {
            return com.mongodb.client.model.Filters.eq(prop.name, value.value)
        }

        fun EQ(prop: KProperty1<*, AnyId>, value: AnyId): Bson {
            return com.mongodb.client.model.Filters.eq(prop.name, value.value)
        }

        fun <T> CONTAINS(prop: KProperty1<*, Set<Id<T>>>, value: Id<T>): Bson {
            return com.mongodb.client.model.Filters.eq(prop.name, value)
        }

        fun CONTAINS(prop: KProperty1<*, Set<AnyId>>, value: AnyId): Bson {
            return com.mongodb.client.model.Filters.eq(prop.name, value.value)
        }

        fun CONTAINS(prop: KProperty1<*, Set<Enum<*>>>, value: Enum<*>): Bson {
            return com.mongodb.client.model.Filters.eq(prop.name, value.name)
        }

        fun CONTAINS_ALL(prop: KMutableProperty1<Audit, Set<AnyId>>, values: Set<AnyId>): Bson {
            return com.mongodb.client.model.Filters.all(prop.name, values.map { it.value })
        }

    }

}

class DbService(val db_url: String, val db_name: String) {
    private val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()
    private val settings =
        MongoClientSettings.builder().applyConnectionString(ConnectionString(db_url)).serverApi(serverApi).build()
    private val mongoClient = MongoClient.create(settings)

    val log = this.logger()
    val db = mongoClient.getDatabase(db_name)
    val audits = db.getCollection<Audit>(collectionName = ime<Audit>())
    val osebe = db.getCollection<Oseba>(collectionName = ime<Oseba>())
    val statusi = db.getCollection<Status>(collectionName = ime<Status>())
    val testi = db.getCollection<Test>(collectionName = ime<Test>())
    val napake = db.getCollection<Napaka>(collectionName = ime<Napaka>())
    val kontakti = db.getCollection<Kontakt>(collectionName = ime<Kontakt>())

    fun sprazni() = db.listCollectionNames().forEach { db.getCollection<Any>(collectionName = it).drop() }

    inline fun <reified T : Entiteta<T>> ustvari(entiteta: T): Boolean {
        return db.getCollection<T>(collectionName = ime<T>()).insertOne(entiteta).wasAcknowledged()
    }

    inline fun <reified T : Entiteta<T>> ustvari(entitete: Collection<T>): Boolean {
        return db.getCollection<T>(collectionName = ime<T>()).insertMany(documents = entitete.toList())
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

    fun audits(entity_id: Set<AnyId>, stran: Int?): List<Audit> {
        val audits = audits.find(
            filter = Filters.CONTAINS_ALL(Audit::entitete_id, entity_id)
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

    fun ucenec(id: Id<Oseba>): UcenecData {
        val aggregation: AggregateIterable<UcenecData> = osebe.aggregate<UcenecData>(
            listOf(
                Aggregates.match(Filters.EQ(id)),
                Aggregates_project_root(Oseba::class),
                /**
                 * Naslovi
                 */
                Aggregates_lookup(
                    from = Naslov::oseba_id,
                    to = Oseba::_id
                ),
                /**
                 * Testi
                 */
                Aggregates_lookup(
                    from = Test::oseba_ucenec_id,
                    to = Oseba::_id,
                    pipeline = listOf(
                        Aggregates_lookup(
                            from = Status::test_id,
                            to = Test::_id,
                            filter = Filters.EQ(Status::oseba_id, id)
                        ),
                        Aggregates_lookup(
                            from = Naloga::_id,
                            to = Test::naloga_id,
                            pipeline = listOf(
                                Aggregates_lookup(
                                    from = Tematika::_id,
                                    to = Naloga::tematika_id
                                )
                            )
                        ),
                    ),
                ),
                /**
                 * Kontakti
                 */
                Aggregates_lookup(
                    from = Kontakt::oseba_id,
                    to = Oseba::_id,
                    pipeline = listOf(
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
                /**
                 * Ucenje
                 */
                Aggregates_lookup(
                    from = Ucenje::oseba_ucenec_id,
                    to = Oseba::_id,
                    pipeline = listOf(
                        Aggregates_lookup(
                            from = Oseba::_id,
                            to = Ucenje::oseba_ucitelj_id,
                        )
                    )
                ),
                /**
                 * Audits
                 */
                Aggregates_lookup(
                    from = Audit::entitete_id,
                    to = Oseba::_id
                )
            )
        )

        aggregation.explain_aggregation()
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
    fun najdi_status(oseba_id: Id<Oseba>, test_id: Id<Test>, naloga_id: Id<Naloga>): Status? {
        return statusi.find(
            filter = Filters.AND(
                Filters.EQ(Status::oseba_id, oseba_id),
                Filters.EQ(Status::test_id, test_id),
                Filters.EQ(Status::naloga_id, naloga_id),
            )
        ).firstOrNull()
    }

    fun status_update(
        id: Id<Status>,
        oseba_id: Id<Oseba>,
        test_id: Id<Test>,
        naloga_id: Id<Naloga>,
        tip: Status.Tip,
        sekund: Int
    ): Status? {
        val r: Status = statusi.findOneAndUpdate(
            filter = Filters.AND(
                Filters.EQ(id),
                Filters.EQ(Status::test_id, test_id),
                Filters.EQ(Status::oseba_id, oseba_id),
                Filters.EQ(Status::naloga_id, naloga_id)
            ),
            update = Updates.set(Status::tip.name, tip),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        ) ?: return null

        val audit = Audit(
            entiteta = Encrypted(ime<Status>()),
            tip = Audit.Tip.STATUS_TIP_POSODOBITEV,
            trajanje = sekund.toDuration(DurationUnit.MINUTES),
            opis = Encrypted(r.tip.name),
            entitete_id = setOf(id.vAnyId(), oseba_id.vAnyId(), test_id.vAnyId(), naloga_id.vAnyId())
        )

        this.ustvari(audit)

        return r
    }

    fun test_update(id: Id<Test>, oseba_id: Id<Oseba>, datum: LocalDate): Test? {
        val r: Test = testi.findOneAndUpdate(
            filter = Filters.AND(
                Filters.EQ(Test::_id, id),
                Filters.CONTAINS(Test::oseba_admin_id, oseba_id),
            ),
            update = Updates.set(Test::deadline.name, datum),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        ) ?: return null

        val audit = Audit(
            entiteta = Encrypted(ime<Test>()),
            tip = Audit.Tip.TEST_DATUM_POSODOBITEV,
            trajanje = Duration.ZERO,
            opis = Encrypted(r.deadline.toString()),
            entitete_id = setOf(id.vAnyId(), oseba_id.vAnyId())
        )

        this.ustvari(audit)

        return r
    }

}
