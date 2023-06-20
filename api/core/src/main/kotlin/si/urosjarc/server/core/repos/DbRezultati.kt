package si.urosjarc.server.core.repos

sealed interface DbPostRezultat<T> {
    class FATAL_DB_NAPAKA<T> : DbPostRezultat<T>
    data class DATA<T>(val data: T) : DbPostRezultat<T>
}

sealed interface DbGetRezultat<T> {
    class ERROR<T> : DbGetRezultat<T>
    data class DATA<T>(val data: T) : DbGetRezultat<T>
}

sealed interface DbDeleteRezultat {
    object ERROR_DB_IZBRIS_NI_DOVOLJEN : DbDeleteRezultat
    object WARN_DELNI_IZBRIS : DbDeleteRezultat
    object PASS : DbDeleteRezultat
}
