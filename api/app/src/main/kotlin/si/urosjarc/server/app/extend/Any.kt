package si.urosjarc.server.app.extend

import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun <T> Any.ukradi_privateProperty(name: String): T {
    return this::class.memberProperties.find { it.name == name }?.apply {
        isAccessible = true
    }?.call(this) as T
}
