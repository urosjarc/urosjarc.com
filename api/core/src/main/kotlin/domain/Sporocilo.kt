package domain

import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Sporocilo(
    override var _id: Id<Sporocilo> = Id(),
    var kontakt_posiljatelj_id: Id<Kontakt>,
    var kontakt_prejemnik_id: Id<Kontakt>,
    val vsebina: String,
) : Entiteta<Sporocilo>
