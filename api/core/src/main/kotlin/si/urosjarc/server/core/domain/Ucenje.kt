package si.urosjarc.server.core.domain

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Ucenje(
    @BsonId
    override var id: ObjectId? = null,
    val ucenec_id: String,
    val ucitelj_id: String
) : Entiteta<Ucenje>()
