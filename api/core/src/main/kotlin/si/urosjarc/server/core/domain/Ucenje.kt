package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import si.urosjarc.server.core.base.Id

@Serializable
data class Ucenje(
    @BsonId @Contextual override var id: Id<Ucenje> = Id(),
    @BsonId @Contextual val ucenec_id: Id<Oseba> = Id(),
    @BsonId @Contextual val ucitelj_id: Id<Oseba> = Id()
) : Entiteta<Ucenje>()
