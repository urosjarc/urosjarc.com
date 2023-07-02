package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Ucenje(
    @BsonId @Contextual
    override var id: ObjectId? = null,
    val ucenec_id: String,
    val ucitelj_id: String
) : Entiteta<Ucenje>()
