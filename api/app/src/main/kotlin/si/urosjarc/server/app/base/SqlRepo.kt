package si.urosjarc.server.app.base

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.core.base.*

abstract class SqlRepo<T : Entiteta<T>>(ime: String) : Table(name = ime), Repo<T> {
    companion object {
        const val STR_SHORT = 20
        const val STR_MEDIUM = 50
        const val STR_LONG = 100
    }

    val id: Column<Int> = integer(Entiteta<Any>::id.name).autoIncrement()
    override val primaryKey = PrimaryKey(id)
    override fun sprazni() = SchemaUtils.drop(this)
    override fun nafilaj() = SchemaUtils.create(this)
    abstract fun zakodiraj(obj: T, any: UpdateBuilder<Number>)
    abstract fun dekodiraj(row: (Column<*>) -> Any?): T
    private fun dekodiraj(query: Query, alias: (Column<*>) -> Column<*>): List<T> =
        query.map { rr -> this.dekodiraj { rr[alias(it)] } }

    override fun ustvari(entiteta: T): DbUstvariRezultat<T> {
        val result: ResultRow? = insert(body = { this.zakodiraj(entiteta, it) }).resultedValues?.get(0)

        return when (result == null) {
            false -> DbUstvariRezultat.DATA(data = dekodiraj { result[it] ?: throw RuntimeException("Fuck") })
            true -> DbUstvariRezultat.FATAL_DB_NAPAKA()
        }
    }

    override fun dobi(stran: Int): List<T> = dekodiraj(selectAll().limit(n = 100, offset = 100L * stran)) { it }
    override fun dobi(kljuc: Id<T>): DbDobiRezultat<T> {
        val result = select(where = { id eq kljuc.value }).limit(n = 1).singleOrNull()
        return when (result == null) {
            true -> DbDobiRezultat.ERROR()
            false -> DbDobiRezultat.DATA(data = dekodiraj { result[it] ?: throw RuntimeException("FUCK") })
        }
    }

    override fun popravi(entiteta: T): Boolean {
        val result: Int = update(
            where = { id eq entiteta.id.value },
            body = { this.zakodiraj(entiteta, it) },
            limit = 1
        )
        return result == 1
    }

    override fun izbrisi(kljuc: Id<T>): Boolean {
        val result: Int = this.deleteWhere(
            op = { id eq kljuc.value },
            limit = 1
        )
        return result == 1
    }
}
