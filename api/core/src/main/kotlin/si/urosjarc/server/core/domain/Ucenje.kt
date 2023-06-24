package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

@Serializable
data class Ucenje(
    @Contextual
    override val id: Id<Ucenje> = Id(),
    @Contextual
    val id_ucenec: Id<Oseba>,
    @Contextual
    val id_ucitelj: Id<Oseba>
) : Entiteta<Ucenje>()
