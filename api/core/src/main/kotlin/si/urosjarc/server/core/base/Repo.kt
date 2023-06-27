package si.urosjarc.server.core.base

interface Repo<T> {
    fun get(page: Int): List<T>
    fun get(key: Id<T>): DbGetRezultat<T>
    fun post(entity: T): DbPostRezultat<T>
    fun put(entity: T): Boolean
    fun delete(key: Id<T>): Boolean
    fun drop()
    fun seed()
}
