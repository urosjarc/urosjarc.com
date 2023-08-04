package domain

import base.Encrypted
import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Naslov(
    override var _id: Id<Naslov> = Id(),
    var oseba_id: Id<Oseba>,
    @Encrypted val drzava: String,
    @Encrypted val mesto: String,
    @Encrypted val zip: Int,
    @Encrypted val ulica: String,
    @Encrypted val dodatno: String
) : Entiteta<Naslov>
