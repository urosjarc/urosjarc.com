package si.programerski_klub.server.core.domain.uprava

import kotlinx.serialization.Serializable
import si.programerski_klub.server.core.base.Entiteta

@Serializable
data class Naslov(
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta<Naslov>
