@file:OptIn(ExperimentalJsExport::class)

package data

import domain.Naloga
import domain.Tematika
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class ZvezekData(
    val naloga: Naloga,
    val tematika_refs: Array<Tematika> = arrayOf(),
)
