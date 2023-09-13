package domain

import base.Encrypted
import base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Zvezek(
    override var _id: Id<Zvezek> = Id(),
    @Contextual val naslov: Encrypted,
) : Entiteta<Zvezek>
