package si.programerski_klub.server.core.repos

sealed interface DbRezultatShranitve<T> {
    class FATAL_DB_NAPAKA<T> : DbRezultatShranitve<T>
    data class DATA<T>(val data: T) : DbRezultatShranitve<T>
}

sealed interface DbRezultatId<T> {
    class ERROR<T> : DbRezultatId<T>
    data class DATA<T>(val data: T) : DbRezultatId<T>
}

sealed interface DbRezultatIskanja<T> {
    class PASS<T> : DbRezultatIskanja<T>
    data class DATA<T>(val data: T) : DbRezultatIskanja<T>
}

sealed interface DbRezultatIzbrisa {
    object ERROR_DB_IZBRIS_NI_DOVOLJEN : DbRezultatIzbrisa
    object WARN_DELNI_IZBRIS : DbRezultatIzbrisa
    object PASS : DbRezultatIzbrisa
}
