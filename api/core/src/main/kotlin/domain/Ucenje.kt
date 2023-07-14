package domain

import kotlinx.serialization.Serializable

@Serializable
data class Ucenje(
    override var _id: String? = null,
    var oseba_ucenec_id: String? = null,
    var oseba_ucitelj_id: String? = null,
) : Entiteta
