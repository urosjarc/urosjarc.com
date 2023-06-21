package si.urosjarc.server.core.domain

import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

data class Sporocilo(
    val id_kontakt: Id<Kontakt>,
    val vsebina: String,
) : Entiteta<Sporocilo>()
