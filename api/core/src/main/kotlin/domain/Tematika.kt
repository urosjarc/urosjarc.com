package domain

import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Tematika(
    override var _id: Id<Tematika> = Id(),
    var zvezek_id: Id<Zvezek>,
    val naslov: String,
) : Entiteta<Tematika>
