package si.urosjarc.server.core.base

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.extend.vBase64
import java.util.*

@JvmInline
@Serializable
value class Id<T>(val value: String) {
    companion object {
        @JvmStatic
        inline fun <reified T : Any> new(): Id<T> {
            return Id("${ime<T>()}_${UUID.randomUUID().vBase64()}")
        }
    }
}
