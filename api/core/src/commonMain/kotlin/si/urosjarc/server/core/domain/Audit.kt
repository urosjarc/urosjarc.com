@file:OptIn(ExperimentalJsExport::class)

package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
data class Audit(
    @Contextual override var _id: String? = null,
    @Contextual var entiteta_id: String? = null,
    val opis: String,
    val entiteta: String
) : Entiteta
