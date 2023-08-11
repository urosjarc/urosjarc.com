package domain

import base.Encrypted
import base.Hashed
import base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Oseba(
    override var _id: Id<Oseba> = Id(),
    var tip: MutableSet<Tip>,
    val geslo: Hashed,
    @Contextual val username: Encrypted,
    @Contextual val ime: Encrypted,
    @Contextual val priimek: Encrypted,
) : Entiteta<Oseba> {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN, KONTAKT, SERVER }
}
