package domain

import base.Encrypted
import base.Hashed
import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Oseba(
    override var _id: Id<Oseba> = Id(),
    @Hashed val username: String,
    @Hashed val geslo: String,
    @Encrypted var tip: MutableSet<Tip>,
    @Encrypted val ime: String,
    @Encrypted val priimek: String,
) : Entiteta<Oseba> {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN, KONTAKT, SERVER }
}
