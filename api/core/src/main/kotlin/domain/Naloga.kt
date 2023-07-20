
package domain

import kotlinx.serialization.Serializable

@Serializable
data class Naloga(
    override var _id: String? = null,
    var tematika_id: String,
    val resitev: String,
    val vsebina: String,
) : Entiteta
