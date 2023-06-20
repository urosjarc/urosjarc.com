package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.repos.DbRezultatShranitve
import kotlin.reflect.KClass

abstract class SqlRepo<T : Entiteta<T>>(cls: KClass<T>) : Table(name = cls::class.java.simpleName.toString()) {
    val id: Column<Int> = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    abstract fun map(obj: T, any: UpdateBuilder<Number>)
    abstract fun resultRow(R: ResultRow): T;
    fun query(query: Query): List<T> = query.map { this.resultRow(it) }

    fun post(entity: T): DbRezultatShranitve<T> {
        val result: ResultRow? = insert({ this.map(entity, it) }).resultedValues?.get(0)

        return when (result == null) {
            false -> DbRezultatShranitve.DATA(data = resultRow(result))
            true -> DbRezultatShranitve.FATAL_DB_NAPAKA()
        }
    }

    fun get(): List<T> = query(selectAll())

    fun get(uid: Id<T>): T = resultRow(select { id eq uid.value }.first())

    fun put(n: T, body: Table.(UpdateBuilder<Number>) -> Unit): Boolean {
        update({ NalogeSqlRepo.id eq n.id.value }, 1, body)

        return true
    }

    fun delete(uid: Id<T>) {
        this.deleteWhere { id eq uid.value }
    }


}
