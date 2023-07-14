package domain

import extends.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Audit(
    override var _id: String? = null,
    var entitete_id: List<String> = listOf(),
    val tip: Tip,
    val opis: String,
    val trajanje: Duration,
    val entiteta: String,
    val ustvarjeno: LocalDateTime = LocalDateTime.zdaj()
) : Entiteta {
    enum class Tip {
        STATUS_POSODOBITEV
    }
}
