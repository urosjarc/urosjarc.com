package domain

import base.Id
import kotlinx.serialization.Serializable

@Serializable
data class Naslov(
    override var _id: Id<Naslov> = Id(),
    var oseba_id: Id<Oseba>,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta<Naslov>
