package core.domain

import core.base.Encrypted
import core.base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Naloga(
    override var _id: Id<Naloga> = Id(),
    var tematika_id: Id<Tematika>,
    @Contextual val resitev: Encrypted,
    @Contextual val vsebina: Encrypted,
    @Contextual val meta: Encrypted,
) : Entiteta<Naloga>
