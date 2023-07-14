package domain

import kotlinx.serialization.Serializable

@Serializable
data class Kontakt(
    override var _id: String? = null,
    var oseba_id: String? = null,
    val data: String,
    val tip: Tip
) : Entiteta {
    enum class Tip { EMAIL, TELEFON }
}
