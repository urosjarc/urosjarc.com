package domain

import base.Encrypted
import base.Id
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable


@Serializable
data class Test(
    override var _id: Id<Test> = Id(),
    var oseba_admin_id: MutableSet<Id<Oseba>>,
    var oseba_ucenec_id: MutableSet<Id<Oseba>>,
    var naloga_id: MutableSet<Id<Naloga>>,
    @Encrypted val naslov: String,
    @Encrypted val podnaslov: String,
    @Encrypted val deadline: LocalDate,
) : Entiteta<Test>
