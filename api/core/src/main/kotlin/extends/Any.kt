package extends

inline fun <reified T : Any> ime(): String = T::class.simpleName.toString()
