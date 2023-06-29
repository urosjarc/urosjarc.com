package si.urosjarc.server.core.base

interface Repo<T> {
    fun dobi(stran: Int): List<T>
    fun dobi(kljuc: Id<T>): DbDobiRezultat<T>
    fun ustvari(entiteta: T): DbUstvariRezultat<T>
    fun popravi(entiteta: T): Boolean
    fun izbrisi(kljuc: Id<T>): Boolean
    fun sprazni()
    fun nafilaj()
}
