package si.urosjarc.server.core.base

import si.urosjarc.server.core.extends.toBase64
import java.util.UUID


@JvmInline
value class Id<T>(val value: String = UUID.randomUUID().toBase64())

inline fun <reified T : Any> name(): String = T::class.simpleName.toString()
