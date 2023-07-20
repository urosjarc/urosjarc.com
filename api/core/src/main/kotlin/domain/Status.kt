package domain

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    override var _id: String? = null,
    var naloga_id: String,
    var test_id: String,
    val tip: Tip = Tip.NEZACETO,
    val pojasnilo: String
) : Entiteta {
    enum class Tip { NEZACETO, NERESENO, NAPACNO, PRAVILNO }
}
