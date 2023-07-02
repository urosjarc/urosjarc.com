package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Id

@Serializable
data class Ucenje(
    @SerialName("_id")
    @Contextual override var id: Id<Ucenje> = Id(),
    @Contextual val ucenec_id: Id<Oseba> = Id(),
    @Contextual val ucitelj_id: Id<Oseba> = Id()
) : Entiteta<Ucenje>()
