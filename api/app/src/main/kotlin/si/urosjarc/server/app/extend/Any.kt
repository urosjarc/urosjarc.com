package si.urosjarc.server.app.extend

inline fun <reified T : Any> ime(): String = T::class.simpleName.toString()
