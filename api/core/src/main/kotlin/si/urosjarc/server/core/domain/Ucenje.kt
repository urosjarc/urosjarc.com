package si.urosjarc.server.core.domain

import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

data class Ucenje(
    override val id: Id<Ucenje> = Id(),
    val id_ucenec: Id<Oseba>,
    val id_ucitelj: Id<Oseba>
) : Entiteta<Ucenje>(id = id)
