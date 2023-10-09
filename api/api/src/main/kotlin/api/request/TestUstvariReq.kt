package api.request

import core.base.Encrypted
import core.base.Id
import core.domain.Naloga
import core.domain.Oseba
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class TestUstvariReq(
    @Contextual val naslov: Encrypted,
    @Contextual val podnaslov: Encrypted,
    val deadline: LocalDate,
    val oseba_ucenci_id: List<Id<Oseba>>,
    val oseba_admini_id: MutableList<Id<Oseba>>,
    val naloga_id: List<Id<Naloga>>
)
