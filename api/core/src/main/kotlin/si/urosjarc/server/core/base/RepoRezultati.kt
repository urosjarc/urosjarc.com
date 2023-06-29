package si.urosjarc.server.core.base

sealed interface DbUstvariRezultat<T> {
    class FATAL_DB_NAPAKA<T> : DbUstvariRezultat<T>
    data class DATA<T>(val data: T) : DbUstvariRezultat<T>
}

sealed interface DbDobiRezultat<T> {
    class ERROR<T> : DbDobiRezultat<T>
    data class DATA<T>(val data: T) : DbDobiRezultat<T>
}

sealed interface DbZbrisiRezultat {
    object ERROR_DB_IZBRIS_NI_DOVOLJEN : DbZbrisiRezultat
    object WARN_DELNI_IZBRIS : DbZbrisiRezultat
    object PASS : DbZbrisiRezultat
}
