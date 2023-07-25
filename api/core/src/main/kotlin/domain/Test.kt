package domain

import base.Id
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable


@Serializable
data class Test(
    override var _id: Id<Test> = Id(),
    var oseba_admin_id: MutableSet<Id<Oseba>>,
    var oseba_ucenec_id: MutableSet<Id<Oseba>>,
    var naloga_id: MutableSet<Id<Naloga>>,
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
) : Entiteta<Test>
