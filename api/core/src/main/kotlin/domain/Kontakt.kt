package domain

import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Kontakt(
    override var _id: Id<Kontakt> = Id(),
    var oseba_id: MutableSet<Id<Oseba>>, // Mama in sin si lahko delita kontakt!
    val data: String,
    val tip: Tip
) : Entiteta<Kontakt> {
    enum class Tip { EMAIL, TELEFON }
}
