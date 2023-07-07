@file:OptIn(ExperimentalJsExport::class)

package core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class Ucenje(
    override var _id: String? = null,
    var oseba_ucenec_id: String? = null,
    var oseba_ucitelj_id: String? = null,
) : Entiteta
