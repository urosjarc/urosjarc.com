package domain

import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Kontakt(
    override var _id: Id<Kontakt> = Id(),
    var oseba_id: Id<Oseba>,
    val data: String,
    val tip: Tip
) : Entiteta<Kontakt> {
    enum class Tip { EMAIL, TELEFON }
}
