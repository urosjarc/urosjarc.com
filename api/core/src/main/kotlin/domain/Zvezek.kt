package domain

import base.Encrypted
import base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Zvezek(
    override var _id: Id<Zvezek> = Id(),
    val tip: Tip,
    @Contextual val naslov: Encrypted,
) : Entiteta<Zvezek> {
    enum class Tip { DELOVNI, TEORETSKI }
}
