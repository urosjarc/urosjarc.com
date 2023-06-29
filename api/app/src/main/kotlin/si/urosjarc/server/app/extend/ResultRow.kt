package si.urosjarc.server.app.extend

import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ExpressionAlias
import org.jetbrains.exposed.sql.ResultRow


fun ResultRow.obisci_stolpce(column: (column: Column<*>, name: String, value: Any?) -> Unit) {
    // ACCESS TO PRIVATE DATA FIELD!
    val dataProperty = this.ukradi_privateProperty<Array<*>>(name = "data")
    this.fieldIndex.entries.forEach { entry ->
        val entry_name = when (entry.key) {
            is Column<*> -> (entry.key as Column<*>).name
            is ExpressionAlias<*> -> (entry.key as ExpressionAlias<*>).alias
            else -> {
                this.logger().error(entry); "NULL"
            }
        }
        val delegateProperty = entry.key.ukradi_privateProperty<Column<*>>(name = "delegate")
        column(delegateProperty, entry_name, dataProperty[entry.value])
    }
}
