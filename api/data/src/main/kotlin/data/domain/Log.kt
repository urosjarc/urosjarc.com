package data.domain

import core.extend.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Log(
    val data: String,
    val tip: Tip,
    val ustvarjeno: LocalDateTime = LocalDateTime.zdaj(),
) {
    enum class Tip { INFO, WARN, ERROR, DEBUG }

    override fun toString(): String {
        val t = ustvarjeno.time
        return "${ustvarjeno.date} ${t.hour}.${t.minute} | $tip: $data"
    }
}
