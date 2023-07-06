package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Ucenje(
    @Contextual override var _id: ObjectId? = null,
    @Contextual var oseba_ucenec_id: ObjectId? = null,
    @Contextual var oseba_ucitelj_id: ObjectId? = null,
) : Entiteta
