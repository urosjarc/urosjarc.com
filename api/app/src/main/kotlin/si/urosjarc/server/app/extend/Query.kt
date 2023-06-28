package si.urosjarc.server.app.extend

import kotlinx.serialization.json.*
import org.jetbrains.exposed.sql.Query
import si.urosjarc.server.core.base.DomainMap

fun Query.toDomainMap(children: Boolean = false): DomainMap {
    //                          table                id                key       value
    val returned = mutableMapOf<String, MutableMap<String, MutableMap<String, JsonElement>>>()
    //                                     table                id                key       references
    val returned_references = mutableMapOf<String, MutableMap<String, MutableMap<String, MutableSet<JsonElement>>>>()

    this.forEach {
        //                           table                key     value
        val splited_row = mutableMapOf<String, MutableMap<String, JsonElement>>()
        //                                        child           parent parameter
        val splited_row_references = mutableMapOf<String, MutableSet<String>>()

        /**
         * SPLITING ROW TO MULTIPLE ROWS
         */
        it.columns { column, key, value ->
            if (key.contains("__")) {
                val nameInfo = key.split("__", limit = 2)
                val table = nameInfo.first()
                val parameter = nameInfo.last()
                splited_row
                    .getOrPut(table) { mutableMapOf() }
                    .set(parameter, column.toJsonElement(key, value))
                if (children && parameter.endsWith("_id")) {
                    splited_row_references
                        .getOrPut(table) { mutableSetOf(parameter) }
                }
            }
        }

        /**
         * MERGING SPLITED REFERENCE WITH RETURNED REFERENCES
         */
        if (children) for ((table, references) in splited_row_references) {
            val child_table = table
            val child_id = splited_row[table]?.get("id") ?: continue

            for (reference in references) {
                val parent_table = reference.split("_").first()
                val parent_id = splited_row[table]?.get(reference) ?: continue

                returned_references
                    .getOrPut(parent_table) { mutableMapOf() }
                    .getOrPut(parent_id.toString()) { mutableMapOf() }
                    .getOrPut("_otroci") { mutableSetOf() } //TODO: MAKE THIS SAFE!
                    .add(child_id)
            }
        }

        /**
         * MERGING SPLITED WITH RETURNED
         */
        for ((table, row: MutableMap<String, JsonElement>) in splited_row) {
            if (row.contains("id")) {
                val id = row["id"].toString()
                returned
                    .getOrPut(table) { mutableMapOf() }
                    .putIfAbsent(id, row)
            }
        }
    }

    /**
     * MERGING PARENTS WITH REFERENCES
     */
    if (children) for ((table, table_data) in returned_references) {
        if (table in returned) {
            val returned_ids = returned[table] ?: continue
            for ((id, row) in table_data) {
                val returned_row = returned_ids[id] ?: continue
                for ((key, value) in row) {
                    returned_row.getOrPut(key) { JsonArray(value.toList())}
                }
            }
        }
    }

    /**
     * CONVERT TO DOMAIN MAP!
     */
    val json = Json {
        this.isLenient = true
        this.ignoreUnknownKeys = true
    }
    val jsonElement = json.encodeToJsonElement(returned)
    return json.decodeFromJsonElement(jsonElement)
}
