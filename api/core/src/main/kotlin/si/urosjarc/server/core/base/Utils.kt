package si.urosjarc.server.core.base


@JvmInline
value class Id<T>(val value: Int = -1)

inline fun <reified T : Any> name(): String = T::class.simpleName.toString()
