@file:OptIn(ExperimentalJsExport::class)

package domain

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class Zvezek(
    override var _id: String? = null,
    val tip: Tip,
    val naslov: String,
) : Entiteta {
    enum class Tip { DELOVNI, TEORETSKI }
}

@JsExport
@Serializable
data class Naloga(
    override var _id: String? = null,
    var tematika_id: String? = null,
    val resitev: String,
    val vsebina: String,
) : Entiteta


@JsExport
@Serializable
data class Tematika(
    override var _id: String? = null,
    var zvezek_id: String? = null,
    val naslov: String,
) : Entiteta
