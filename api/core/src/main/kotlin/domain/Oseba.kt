package domain

import kotlinx.serialization.Serializable

@Serializable
data class Oseba(
    override var _id: String? = null,
    val ime: String,
    val priimek: String,
    val username: String,
    val tip: Tip,
) : Entiteta {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN }
}
