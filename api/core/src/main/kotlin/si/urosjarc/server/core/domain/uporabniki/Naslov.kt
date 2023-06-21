package si.urosjarc.server.core.domain.uporabniki

import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

data class Naslov(
    val id_oseba: Id<Oseba>,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta<Naslov>()
