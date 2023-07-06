package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.types.ObjectId

data class Audit(
    @Contextual override var _id: ObjectId? = null,
    @Contextual var entiteta_id: ObjectId? = null,
    val opis: String,
    val entiteta: String
) : Entiteta
