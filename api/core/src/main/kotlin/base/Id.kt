package base

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@JvmInline
@Serializable
value class Id<T>(@Contextual val value: ObjectId = ObjectId()) {
    fun value(): AnyId {
        return AnyId(this.value)
    }
}

@JvmInline
@Serializable
value class AnyId(@Contextual val value: ObjectId = ObjectId())
