@file:OptIn(ExperimentalJsExport::class)

package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class Zvezek(
    @Contextual override var _id: String? = null,
    val tip: Tip,
    val naslov: String,
) : Entiteta {
    enum class Tip { DELOVNI, TEORETSKI }
}

@JsExport
@Serializable
data class Naloga(
    @Contextual override var _id: String? = null,
    @Contextual var tematika_id: String? = null,
    val resitev: String,
    val vsebina: String,
) : Entiteta


@JsExport
@Serializable
data class Tematika(
    @Contextual override var _id: String? = null,
    @Contextual var zvezek_id: String? = null,
    val naslov: String,
) : Entiteta
