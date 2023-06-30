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
    val ucenec_id: Id<Oseba>,
    @Contextual
    val ucitelj_id: Id<Oseba>
) : Entiteta<Ucenje>()
