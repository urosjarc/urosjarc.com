package domain

import kotlinx.serialization.Serializable

@Serializable
data class Zvezek(
    override var _id: String? = null,
    val tip: Tip,
    val naslov: String,
) : Entiteta {
    enum class Tip { DELOVNI, TEORETSKI }
}
