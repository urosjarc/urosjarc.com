package domain

import base.Id
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable


@Serializable
data class Test(
    override var _id: Id<Test> = Id(),
    var oseba_id: Id<Oseba>,
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
) : Entiteta<Test>
