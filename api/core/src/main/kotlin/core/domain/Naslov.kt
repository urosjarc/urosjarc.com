package core.domain

import core.base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Naslov(
    override var _id: Id<Naslov> = Id(),
    var oseba_id: Id<Oseba>,
    @Contextual val drzava: core.base.Encrypted,
    @Contextual val mesto: core.base.Encrypted,
    @Contextual val zip: core.base.Encrypted,
    @Contextual val ulica: core.base.Encrypted,
    @Contextual val dodatno: core.base.Encrypted
) : Entiteta<Naslov>
