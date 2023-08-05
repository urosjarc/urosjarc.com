package domain

import base.Encrypted
import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Naloga(
    override var _id: Id<Naloga> = Id(),
    var tematika_id: Id<Tematika>,
    val resitev: Encrypted,
    val vsebina: Encrypted,
) : Entiteta<Naloga>
