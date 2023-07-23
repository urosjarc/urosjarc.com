package base

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
private fun UUID(): String {
    return Base64.encode(source = ObjectId().toByteArray().reversedArray())
}

@JvmInline
@Serializable
value class Id<T>(val value: String = UUID()) {
    fun vAnyId(): AnyId {
        return AnyId(this.value)
    }
}

@JvmInline
@Serializable
value class AnyId(val value: String = UUID())
