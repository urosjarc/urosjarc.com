package si.urosjarc.server.core.repos

import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Field
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.MongoCollection
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.types.ObjectId
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.extend.Aggregates_lookup
import si.urosjarc.server.core.extend.Aggregates_project_root


class OsebaRepo(val collection: MongoCollection<Oseba>) {


    fun profil(id: ObjectId): OsebaData {
        val aggregation = collection.aggregate<OsebaData>(
            listOf(
                Aggregates.match(Filters.eq(Oseba::_id.name, id)),
                Aggregates_project_root(Oseba::class),
                Aggregates_lookup(
                    from = Naslov::oseba_id,
                    to = Oseba::_id
                ),
                Aggregates_lookup(
                    from = Test::oseba_id,
                    to = Oseba::_id,
                    pipeline = listOf(
                        Aggregates_lookup(
                            from = Status::test_id,
                            to = Test::_id,
                            pipeline = listOf(
                                Aggregates.addFields(
                                    Field("opravljeno", Aggregates.count("\$status_refs"))
                                )
                            )
                        ),
                    )
                ),
                Aggregates_lookup(
                    from = Kontakt::oseba_id,
                    to = Oseba::_id,
                    pipeline = listOf(
                        Aggregates_lookup(
                            from = Sporocilo::kontakt_prejemnik_id,
                            to = Kontakt::_id,
                            pipeline = listOf(
                                Aggregates_lookup(
                                    from = Kontakt::_id,
                                    to = Sporocilo::kontakt_posiljatelj_id,
                                    pipeline = listOf(
                                        Aggregates_lookup(
                                            from = Oseba::_id,
                                            to = Kontakt::oseba_id
                                        ),
                                    )
                                ),
                            )
                        ),
                        Aggregates_lookup(
                            from = Sporocilo::kontakt_posiljatelj_id,
                            to = Kontakt::_id,
                            pipeline = listOf(
                                Aggregates_lookup(
                                    from = Kontakt::_id,
                                    to = Sporocilo::kontakt_prejemnik_id,
                                    pipeline = listOf(
                                        Aggregates_lookup(
                                            from = Oseba::_id,
                                            to = Kontakt::oseba_id
                                        ),
                                    )
                                ),
                            )
                        ),
                    )
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
            )
        )


        val json = Json {
            this.prettyPrint = true
        }
        println(json.encodeToString(aggregation.explain()))

        return aggregation.first()


    }
}
