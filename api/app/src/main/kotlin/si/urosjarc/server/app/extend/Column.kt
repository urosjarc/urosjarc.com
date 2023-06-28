package si.urosjarc.server.app.extend

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.JavaLocalDateColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalDateTimeColumnType
import si.urosjarc.server.core.extends.toLocalDate
import si.urosjarc.server.core.extends.toLocalDateTime

fun Column<*>.toJsonElement(key: String, value: Any?): JsonElement {
    if (value == null) return JsonNull

    return when (this.columnType) {
        is JavaLocalDateColumnType -> JsonPrimitive((value as Long).toLocalDate().toString())
        is JavaLocalDateTimeColumnType -> JsonPrimitive((value as Long).toLocalDateTime().toString())
        is IntegerColumnType -> JsonPrimitive(value as Int)
        is FloatColumnType -> JsonPrimitive(value as Float)
        is VarCharColumnType -> JsonPrimitive(value as String)
        is BooleanColumnType -> JsonPrimitive(value as Boolean)
        else -> throw RuntimeException("Could not convert ${this.columnType} -> $key = $value")
    }
}
