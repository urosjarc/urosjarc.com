package si.urosjarc.server.core.domain

import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

data class Naloga(
    override val id: Id<Naloga> = Id(),
    val name: String
) : Entiteta<Naloga>
