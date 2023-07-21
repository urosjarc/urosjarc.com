package base

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@JvmInline
@Serializable
value class Id<T>(
    @Contextual private val value: ObjectId = ObjectId()
)

@JvmInline
@Serializable
value class AnyId(
    @Contextual private val value: ObjectId = ObjectId()
)
