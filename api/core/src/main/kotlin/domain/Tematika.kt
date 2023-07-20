package domain

import kotlinx.serialization.Serializable

@Serializable
data class Tematika(
    override var _id: String? = null,
    var zvezek_id: String,
    val naslov: String,
) : Entiteta
