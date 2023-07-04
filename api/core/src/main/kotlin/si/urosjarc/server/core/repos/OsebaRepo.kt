package si.urosjarc.server.core.repos

import com.mongodb.ExplainVerbosity
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.MongoCollection
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.bson.Document
import org.bson.json.JsonWriterSettings
import org.bson.types.ObjectId
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.extend.Aggregates_lookup
import si.urosjarc.server.core.extend.Aggregates_project_root
import si.urosjarc.server.core.serializers.ObjectIdSerializer


class OsebaRepo(val collection: MongoCollection<Oseba>) {

    @Serializable
    data class DbSporocilo(
        val sporocilo: Sporocilo,
        val kontakt_posiljatelj: Oseba? = null,
        val kontakt_prejemnik: Oseba? = null,
    )

    @Serializable
    data class DbKontakt(
        val kontakt: Kontakt,
        val sporocila_poslana: List<DbSporocilo>,
        val sporocila_prejeta: List<DbSporocilo>
    )

    @Serializable
    data class DbUcenje(
        val ucenje: Ucenje,
        val oseba_ucenec: Oseba? = null,
        val oseba_ucitelj: Oseba? = null,
    )

    @Serializable
    data class DbTesti(
        val test: Test,
        val statusi: List<Status>
    )

    @Serializable
    data class DbProfil(
        val oseba: Oseba,
        val kontakti: List<DbKontakt>,
        val naslovi: List<Naslov>,
        val ucenje_ucenci: List<DbUcenje>,
        val ucenje_ucitelji: List<DbUcenje>,
        val testi: List<DbTesti>
    )

    /**
     * [
     *   {
     *     $match: {
     *       _id: ObjectId("64a20e0c65e828693428a8c1"),
     *     },
     *   },
     *   { $project: { oseba: "$$ROOT" } },
     *   {
     *     $lookup: {
     *       from: "Test",
     *       localField: "_id",
     *       foreignField: "oseba_id",
     *       pipeline: [
     *         {
     *           $project: {
     *             test: "$$ROOT",
     *           },
     *         },
     *         {
     *           $lookup: {
     *             from: "Status",
     *             localField: "_id",
     *             foreignField: "test_id",
     *             as: "statusi",
     *           },
     *         },
     *       ],
     *       as: "testi",
     *     },
     *   },
     * ]
     */

    fun profil(id: ObjectId) {
        val resultsFlow = collection.aggregate<DbProfil>(
            listOf(
                Aggregates.match(Filters.eq(Oseba::_id.name, id)),
                Aggregates_project_root(Oseba::class),
                Aggregates_lookup(
                    from = Kontakt::oseba_id,
                    to = Oseba::_id,
                    pipeline = listOf(
                        Aggregates_lookup(
                            from = Sporocilo::kontakt_prejemnik_id,
                            to = Kontakt::_id,
                            pipeline = listOf(
                                Aggregates_lookup(
                                    from = Kontakt::oseba_id,
                                    to = Oseba::_id
                                ),
                            )
                        ),
                        Aggregates_lookup(
                            from = Sporocilo::kontakt_posiljatelj_id,
                            to = Kontakt::_id,
                            pipeline = listOf(
                                Aggregates_lookup(
                                    from = Kontakt::oseba_id,
                                    to = Oseba::_id,
                                ),
                            )
                        ),
                    )
                ),
                Aggregates_lookup(
                    from = Naslov::oseba_id,
                    to = Oseba::_id
                ),
                Aggregates_lookup(
                    from = Ucenje::oseba_ucenec_id,
                    to = Ucenje::_id,
                    pipeline = listOf(
                        Aggregates_lookup(
                            from = Oseba::_id,
                            to = Ucenje::oseba_ucitelj_id,
                        )
                    )
                ),
                Aggregates_lookup(
                    from = Ucenje::oseba_ucitelj_id,
                    to = Oseba::_id,
                    pipeline = listOf(
                        Aggregates_lookup(
                            from = Oseba::_id,
                            to = Ucenje::oseba_ucenec_id
                        )
                    )
                ),
                Aggregates_lookup(
                    from = Test::oseba_id,
                    to = Oseba::_id,
                    pipeline = listOf(
                        Aggregates_lookup(
                            from = Status::test_id,
                            to = Test::_id,
                        )
                    )
                ),

//                Aggregates.lookup(ime<Ucenje>(), "_id", "oseba_ucenec_id", "ucitelji"),
//                Aggregates.lookup(ime<Ucenje>(), "_id", "oseba_ucitelj_id", "ucenci"),
//                Aggregates.unwind("\$testi"),
//                Aggregates.lookup(ime<Test>(), "_id", "oseba_id", "testi.test"),
            )
        )
        val explanation = resultsFlow.explain(ExplainVerbosity.QUERY_PLANNER).get("command") as Document
        val jsonSetting = JsonWriterSettings.builder()
            .indent(true).build()
        println(explanation.toJson(jsonSetting))

        val json = Json(builderAction = {
            serializersModule = SerializersModule {
                contextual(ObjectIdSerializer)
            }
            this.prettyPrint = true
        })
        resultsFlow.forEach {
            println(json.encodeToString(it))
        }
    }
}
