package core.domain

import core.base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Naloga(
    override var _id: Id<Naloga> = Id(),
    var tematika_id: Id<Tematika>,
    @Contextual val resitev: core.base.Encrypted,
    @Contextual val vsebina: core.base.Encrypted,
    @Contextual val meta: core.base.Encrypted
) : Entiteta<Naloga>
