package domain

import kotlinx.serialization.Serializable

@Serializable
data class Sporocilo(
    override var _id: String? = null,
    var kontakt_posiljatelj_id: String,
    var kontakt_prejemnik_id: String,
    val vsebina: String,
) : Entiteta
