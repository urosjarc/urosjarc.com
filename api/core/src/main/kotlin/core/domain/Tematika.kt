package core.domain

import core.base.Encrypted
import core.base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Tematika(
    override var _id: Id<Tematika> = Id(),
    var zvezek_id: Id<Zvezek>,
    @Contextual val naslov: Encrypted,
) : Entiteta<Tematika>
