package app.repos

import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.AggregateIterable
import com.mongodb.kotlin.client.MongoCollection
import core.domain.*
import app.extend.Aggregates_lookup
import app.extend.Aggregates_project_root
import app.extend.explain_aggregation
import core.data.OsebaData

class OsebaRepo(val collection: MongoCollection<Oseba>) {


    fun profil(id: String): OsebaData {
        val aggregation: AggregateIterable<OsebaData> = collection.aggregate<OsebaData>(
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

        aggregation.explain_aggregation()

        return aggregation.first()


    }
}
