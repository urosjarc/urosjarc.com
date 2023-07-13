@file:OptIn(ExperimentalJsExport::class)

package data

import domain.Oseba
import domain.Ucenje
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class UcenjeData(
    val ucenje: Ucenje,
    val oseba_refs: Array<Oseba> = arrayOf(),
    val ucenje_ucenec_refs: Array<UcenjeData> = arrayOf(),
)
