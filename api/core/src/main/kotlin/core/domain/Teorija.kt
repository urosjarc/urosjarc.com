package core.domain

import core.base.Encrypted
import core.base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Teorija(
    override var _id: Id<Teorija> = Id(),
    var tematika_id: Id<Tematika>,
    @Contextual val vsebina: Encrypted,
    @Contextual val meta: Encrypted,
) : Entiteta<Teorija>
