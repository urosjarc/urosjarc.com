package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Ucenje(
    @Contextual override var _id: String? = null,
    @Contextual var oseba_ucenec_id: String? = null,
    @Contextual var oseba_ucitelj_id: String? = null,
) : Entiteta
