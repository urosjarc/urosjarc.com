@file:OptIn(ExperimentalJsExport::class)

package core.domain

import kotlinx.serialization.Contextual
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
data class Audit(
    override var _id: String? = null,
    var entiteta_id: String? = null,
    val opis: String,
    val entiteta: String
) : Entiteta
