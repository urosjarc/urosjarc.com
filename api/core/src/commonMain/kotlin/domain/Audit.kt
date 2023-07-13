@file:OptIn(ExperimentalJsExport::class)

package domain

import extends.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.time.Duration

@JsExport
@Serializable
data class Audit(
    override var _id: String? = null,
    var entitete_id: Array<String> = arrayOf(),
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
