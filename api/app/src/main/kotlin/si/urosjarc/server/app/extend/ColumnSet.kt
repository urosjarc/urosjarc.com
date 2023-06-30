package si.urosjarc.server.app.extend

import org.jetbrains.exposed.sql.*
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Entiteta
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

fun ColumnSet.vzemi(vararg tables: Table, cb: (SqlExpressionBuilder.() -> Op<Boolean>)? = null): DomenskiGraf {
    val columns = mutableListOf<ExpressionAlias<*>>()

    /**
     * CREATE SQL REPO MAP
     */
    val names_tables = mutableMapOf<String, Table>()
    val names_columns_refs = mutableMapOf<String, MutableMap<Column<*>, String>>()
    for (table in tables) {
        val tableName = table.tableName.lowercase()
        names_tables.putIfAbsent(tableName, table)
        for (column in table.columns) {
            columns.add(column.alias("${tableName}__${column.name}"))
            if (column.name.endsWith("_id")) {
                val parentTableName = column.name.replace("_id", "")
                names_columns_refs
                    .getOrPut(tableName) { mutableMapOf() }
                    .putIfAbsent(column, parentTableName)
            }
        }
    }

    /**
     * CREATE DOMAIN MAP
     */
    val name_domain_map = mutableMapOf<String, KProperty1<*, *>>()
    for (prop in DomenskiGraf::class.memberProperties) {
        name_domain_map.putIfAbsent(prop.name, prop)
    }

    /**
     * INSERT TO DOMAIN MAP
     */
    val domenskiGraf = DomenskiGraf()
    val slice = Slice(this, columns)

    when (cb == null) {
        true -> slice.selectAll()
        false -> slice.select(where = cb)
    }.forEach { resultRow ->
        for ((tableName, table) in names_tables) {
            name_domain_map[tableName]?.let { prop ->
                val entiteta: Entiteta<*> = when(table){
                    is SqlRepo<*> -> table.dekodiraj(resultRow)
                    is Alias<*> -> (table.delegate as SqlRepo<*>).dekodiraj(resultRow)
                    else -> throw Exception("Could not convert!")
                }
                (prop.call(domenskiGraf) as MutableMap<Int, Entiteta<*>>)?.putIfAbsent(entiteta.id.value, entiteta)

                names_columns_refs.get(tableName)?.forEach { (column, parentTableName) ->
                    val child_id = entiteta.id.value
                    val parent_id = resultRow[column] as Int
                    val parent_table = parentTableName
                    val child_table = tableName
                    domenskiGraf.otroci
                        .getOrPut(parent_table) { mutableMapOf() }
                        .getOrPut(parent_id) { mutableMapOf() }
                        .getOrPut(child_table) { mutableSetOf() }
                        .add(child_id)
                }
            }
        }
    }

    return domenskiGraf
}
