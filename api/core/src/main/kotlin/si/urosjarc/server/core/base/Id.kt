package si.urosjarc.server.core.base

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.extends.name
import si.urosjarc.server.core.extends.toBase64
import java.util.*

@JvmInline
@Serializable
value class Id<T>(val value: String) {
    companion object {
        @JvmStatic
        inline fun <reified T : Any> new(): Id<T> {
            return Id("${name<T>()}_${UUID.randomUUID().toBase64()}")
        }
    }
}
