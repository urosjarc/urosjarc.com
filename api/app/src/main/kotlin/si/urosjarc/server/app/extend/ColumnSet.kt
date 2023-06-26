package si.urosjarc.server.app.extend

import org.jetbrains.exposed.sql.*

fun ColumnSet.sliceAlias(vararg tables: Table): FieldSet {
    val columns = mutableListOf<ExpressionAlias<*>>()

    for (table in tables)
        for (column in table.columns)
            columns.add(column.alias("${table.tableName.lowercase()}__${column.name}"))

    return Slice(this, columns)
}
