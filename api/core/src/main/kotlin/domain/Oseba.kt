package domain

import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Oseba(
    override var _id: Id<Oseba> = Id(),
    val ime: String,
    val priimek: String,
    val username: String,
    val tip: Tip,
) : Entiteta<Oseba> {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN, KONTAKT, SERVER }
}
