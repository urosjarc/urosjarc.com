package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.repos.DbGetRezultat
import si.urosjarc.server.core.repos.DbPostRezultat
import si.urosjarc.server.core.repos.DbRepo

abstract class SqlRepo<T : Entiteta<T>>(table: String) : Table(name = table), DbRepo<T> {

    val id: Column<Int> = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    abstract fun map(obj: T, any: UpdateBuilder<Number>)
    abstract fun resultRow(R: ResultRow): T;
    private fun query(query: Query): List<T> = query.map { this.resultRow(it) }

    override fun drop() = SchemaUtils.drop(this)
    override fun seed() = SchemaUtils.create(this)
    override fun post(entity: T): DbPostRezultat<T> {
        val result: ResultRow? = insert(body = { this.map(entity, it) }).resultedValues?.get(0)

        return when (result == null) {
            false -> DbPostRezultat.DATA(data = resultRow(result))
            true -> DbPostRezultat.FATAL_DB_NAPAKA()
        }
    }

    override fun get(page: Int): List<T> = query(selectAll().limit(n = 100, offset = 100L * page))

    override fun get(key: Id<T>): DbGetRezultat<T> {
        val result = select(where = { id eq key.value }).limit(n = 1).singleOrNull()
        return when (result == null) {
            true -> DbGetRezultat.ERROR()
            false -> DbGetRezultat.DATA(data = resultRow(result))
        }
    }

    override fun put(entity: T): Boolean {
        val result: Int = update(
            where = { id eq entity.id.value },
            body = { this.map(entity, it) },
            limit = 1
        )
        return result == 1;
    }

    override fun delete(key: Id<T>): Boolean {
        val result: Int = this.deleteWhere(
            op = { id eq key.value },
            limit = 1
        )
        return result == 1;
    }
}
