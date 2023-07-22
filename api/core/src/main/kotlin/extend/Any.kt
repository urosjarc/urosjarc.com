package extend

inline fun <reified T : Any> ime(): String = T::class.simpleName.toString()
