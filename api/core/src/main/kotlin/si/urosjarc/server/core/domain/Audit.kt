package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import org.bson.codecs.pojo.annotations.BsonId
import si.urosjarc.server.core.base.Id

data class Audit(
    @BsonId @Contextual override var id: Id<Audit> = Id(),
    @BsonId @Contextual val entiteta_id: Id<Entiteta<Any>>,
    val opis: String,
    val entiteta: String
) : Entiteta<Audit>()
