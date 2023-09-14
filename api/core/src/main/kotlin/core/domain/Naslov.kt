package core.domain

import core.base.Encrypted
import core.base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Naslov(
    override var _id: Id<Naslov> = Id(),
    var oseba_id: Id<Oseba>,
    @Contextual val drzava: Encrypted,
    @Contextual val mesto: Encrypted,
    @Contextual val zip: Encrypted,
    @Contextual val ulica: Encrypted,
    @Contextual val dodatno: Encrypted
) : Entiteta<Naslov>
