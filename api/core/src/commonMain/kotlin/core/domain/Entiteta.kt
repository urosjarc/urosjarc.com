@file:OptIn(ExperimentalJsExport::class)

package core.domain

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport


@JsExport
@Serializable
sealed interface Entiteta {
    var _id: String?
}
