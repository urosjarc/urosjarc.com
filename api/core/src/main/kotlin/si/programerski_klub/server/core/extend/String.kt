package si.programerski_klub.server.core.extend;

import java.util.*

// String extensions
fun String.to_snake_case(): String {
    val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
    return camelRegex.replace(this) {
        "_${it.value}"
    }.lowercase(Locale.getDefault())
}

fun String.to_camel_case(): String {
    val snakeRegex = "_[a-zA-Z]".toRegex()
    return snakeRegex.replace(this) {
        it.value.replace("_", "")
            .uppercase(Locale.getDefault())
    }
}
