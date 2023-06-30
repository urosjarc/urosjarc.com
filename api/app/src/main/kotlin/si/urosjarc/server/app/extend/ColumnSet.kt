package si.urosjarc.server.app.extend

import org.jetbrains.exposed.sql.*
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Entiteta
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

inline fun <reified T : Any> ColumnSet.vzemi(vararg tables: Table): T {
    val columns = mutableListOf<ExpressionAlias<*>>()

    /**
     * CREATE SQL REPO MAP
     */
    val names_tables = mutableMapOf<String, Table>()
    val names_columns_parents = mutableMapOf<String, MutableMap<ExpressionAlias<*>, String>>()
    for (table in tables) {
        val tableName = table.tableName.lowercase()
        names_tables.putIfAbsent(tableName, table)
        for (column in table.columns) {
            /**
             * ALIASING COLUMNS
             */
            val aliased_column = column.alias("${tableName}__${column.name}")
            columns.add(aliased_column)

            /**
             * ADDING COLUMNS REFERENCES
             */
            if (column.name.endsWith("_id")) {
                val parentTableName = column.name.replace("_id", "")
                names_columns_parents
                    .getOrPut(tableName) { mutableMapOf() }
                    .putIfAbsent(aliased_column, parentTableName)
            }
        }
    }

    /**
     * CREATE DOMAIN MAP
     */
    val name_domain_map = mutableMapOf<String, KProperty1<*, *>>()
    for (prop in T::class.memberProperties) {
        name_domain_map.putIfAbsent(prop.name, prop)
    }

    /**
     * INSERT TO DOMAIN MAP
     */
    val domenskiGraf = T::class.java.getDeclaredConstructor().newInstance() as DomenskiGraf
    val slice = Slice(this, columns)

    slice.selectAll().forEach { resultRow ->
        for ((tableName, table) in names_tables) {
            name_domain_map[tableName]?.let { prop ->

                val entiteta: Entiteta<*> = when (table) {
                    is Alias<*> -> (table.delegate as SqlRepo<*>).dekodiraj { resultRow[table[it]] }
                    else -> (table as SqlRepo<*>).dekodiraj { resultRow[it] }
                }

                /**
                 * INJECT TO DOMAIN OBJECT
                 */
                (prop.call(domenskiGraf) as MutableMap<Any, Any>).putIfAbsent(entiteta.id.value, entiteta)

                /**
                 * USTVARI REFERENCO OTROKA!
                 */
                names_columns_parents.get(tableName)?.forEach { (column, parentTableName) ->
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

    return domenskiGraf as T
}
