package si.urosjarc.server.core.base

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Id<T>(val value: Int = -1)
