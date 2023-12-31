package core.domain

import core.base.Encrypted
import core.base.Id
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
data class Test(
    override var _id: Id<Test> = Id(),
    var naloga_id: MutableSet<Id<Naloga>>,
    var oseba_admin_id: MutableSet<Id<Oseba>>,
    var oseba_ucenec_id: MutableSet<Id<Oseba>>,
    val deadline: LocalDate,
    @Contextual val naslov: Encrypted,
    @Contextual val podnaslov: Encrypted,
) : Entiteta<Test>
