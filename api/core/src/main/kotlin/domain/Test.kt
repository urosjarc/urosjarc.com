package domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable


@Serializable
data class Test(
    override var _id: String? = null,
    var oseba_id: String? = null,
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
) : Entiteta
