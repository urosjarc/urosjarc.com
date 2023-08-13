package domain

import base.Id
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Ucenje(
    override var _id: Id<Ucenje> = Id(),
    var oseba_ucenec_id: Id<Oseba>,
    var oseba_ucitelj_id: Id<Oseba>,
    var ustvarjeno: LocalDate
) : Entiteta<Ucenje>
