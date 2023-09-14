package core.domain

import core.base.Hashed
import core.base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Oseba(
    override var _id: Id<Oseba> = Id(),
    var tip: MutableSet<Tip>,
    val geslo: Hashed,
    var letnik: Int,
    @Contextual val username: core.base.Encrypted,
    @Contextual val ime: core.base.Encrypted,
    @Contextual val priimek: core.base.Encrypted,
) : Entiteta<Oseba> {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN, KONTAKT, SERVER }
}
