package domain

import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Ucenje(
    override var _id: Id<Ucenje> = Id(),
    var oseba_ucenec_id: Id<Oseba>,
    var oseba_ucitelj_id: Id<Oseba>,
) : Entiteta<Ucenje>
