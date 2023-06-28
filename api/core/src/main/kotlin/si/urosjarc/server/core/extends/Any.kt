package si.urosjarc.server.core.extends

inline fun <reified T : Any> name(): String = T::class.simpleName.toString()
