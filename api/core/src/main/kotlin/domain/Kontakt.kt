package domain

import base.Encrypted
import base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Kontakt(
    override var _id: Id<Kontakt> = Id(),
    var oseba_id: MutableSet<Id<Oseba>>, // Mama in sin si lahko delita kontakt!
    var tip: Tip,
    @Contextual val data: Encrypted
) : Entiteta<Kontakt> {
    enum class Tip { EMAIL, TELEFON }
}
