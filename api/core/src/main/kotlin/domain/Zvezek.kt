package domain

import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Zvezek(
    override var _id: Id<Zvezek> = Id(),
    val tip: Tip,
    val naslov: String,
) : Entiteta<Zvezek> {
    enum class Tip { DELOVNI, TEORETSKI }
}
