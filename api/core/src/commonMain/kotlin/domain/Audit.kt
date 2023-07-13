@file:OptIn(ExperimentalJsExport::class)

package domain

import extends.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class Audit(
    override var _id: String? = null,
    var entiteta_id: String? = null,
    val tip: Tip,
    val opis: String,
    val entiteta: String,
    val ustvarjeno: LocalDateTime = LocalDateTime.zdaj()
) : Entiteta {
    enum class Tip {
        STATUS_POSODOBITEV
    }
}
