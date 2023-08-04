package domain

import base.Encrypted
import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Naloga(
    override var _id: Id<Naloga> = Id(),
    var tematika_id: Id<Tematika>,
    @Encrypted val resitev: String,
    @Encrypted val vsebina: String,
) : Entiteta<Naloga>
