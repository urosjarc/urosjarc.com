package domain

import base.Encrypted
import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Naslov(
    override var _id: Id<Naslov> = Id(),
    var oseba_id: Id<Oseba>,
    val drzava: Encrypted,
    val mesto: Encrypted,
    val zip: Encrypted,
    val ulica: Encrypted,
    val dodatno: Encrypted
) : Entiteta<Naslov>
