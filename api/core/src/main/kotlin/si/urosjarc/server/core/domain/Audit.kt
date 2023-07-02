package si.urosjarc.server.core.domain

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Audit(
    @BsonId
    override var id: ObjectId? = null,
    val opis: String,
    val entiteta_id: Int,
    val entiteta: String
) : Entiteta<Audit>()
