package domain

import kotlinx.serialization.Serializable

@Serializable
data class Naslov(
    override var _id: String? = null,
    var oseba_id: String? = null,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta
