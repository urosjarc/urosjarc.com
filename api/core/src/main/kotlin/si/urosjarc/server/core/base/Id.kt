package si.urosjarc.server.core.base

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import si.urosjarc.server.core.serializers.IdSerializer


@Serializable(with=IdSerializer::class)
class Id<T>(var value: ObjectId? = null)
