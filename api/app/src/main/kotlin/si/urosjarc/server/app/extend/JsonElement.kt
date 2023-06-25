package si.urosjarc.server.app.extend

import kotlinx.serialization.json.*
import org.jetbrains.exposed.sql.Column
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
    val mutableMap = mutableMapOf<String, Any>()
    val dataList = this::class.memberProperties.find { it.name == "data" }?.apply {
        isAccessible = true
    }?.call(this) as Array<*>
    fieldIndex.entries.forEach { entry ->
        val column = entry.key as Column<*>
        mutableMap[column.name] = dataList[entry.value] as Any
    }
    return mutableMap.toJsonElement()
}

fun Query.toJsonElement(): JsonElement = this.toList().toJsonElement()
