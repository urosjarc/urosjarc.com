package si.urosjarc.server.core.base

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.extends.toBase64
import java.util.*


@JvmInline
@Serializable
value class Id<T>(val value: String = UUID.randomUUID().toBase64()) {
    override fun toString(): String = "${name<Id<T>>()}(${this.value})"
}

inline fun <reified T : Any> name(): String = T::class.simpleName.toString()
