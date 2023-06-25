package si.urosjarc.server.app.extend

import kotlinx.serialization.json.*
import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ExpressionAlias
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

private fun Any?.toJsonElement(): JsonElement {
    return when (this) {
        null -> JsonNull
        is JsonElement -> this
        is Boolean -> JsonPrimitive(this)
        is Number -> JsonPrimitive(this)
        is String -> JsonPrimitive(this)
        is ResultRow -> this.toJsonElement()
        is Iterable<*> -> JsonArray(this.map { it.toJsonElement() })
        is Map<*, *> -> JsonObject(this.map { it.key.toString() to it.value.toJsonElement() }.toMap())
        else -> throw Exception("PROBLEM:  ${this::class}=${this}}")
    }
}


fun ResultRow.toJsonElement(): JsonElement {
    val log = this.logger()

    val mutableMap = mutableMapOf<String, Any>()
    val dataList = this::class.memberProperties.find { it.name == "data" }?.apply {
        isAccessible = true
    }?.call(this) as Array<*>
    fieldIndex.entries.forEach { entry ->
        val column_name = when (entry.key) {
            is Column<*> -> (entry.key as Column<*>).name
            is ExpressionAlias<*> -> (entry.key as ExpressionAlias<*>).alias
            else -> {
                log.error(entry); "NULL"
            }
        }
        mutableMap[column_name] = dataList[entry.value] as Any
    }
    return mutableMap.toJsonElement()
}

fun Query.toJsonElement(): JsonElement = this.toList().toJsonElement()
