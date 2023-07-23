package base

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@JvmInline
@Serializable
value class Id<T>(val value: String = ObjectId().toHexString()) {
    fun vAnyId(): AnyId {
        return AnyId(this.value)
    }
}

@JvmInline
@Serializable
value class AnyId(val value: String = ObjectId().toHexString())
