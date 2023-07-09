@file:OptIn(ExperimentalJsExport::class)

package core.domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport


@JsExport
@Serializable
data class Test(
    override var _id: String? = null,
    var oseba_id: String? = null,
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
) : Entiteta

@JsExport
@Serializable
data class Status(
    override var _id: String? = null,
    var naloga_id: String? = null,
    var test_id: String? = null,
    val tip: Tip = Tip.NEOPRAVLJENO,
    val pojasnilo: String
) : Entiteta {
    enum class Tip { NEOPRAVLJENO, NERESENO, NAPACNO, PRAVILNO }
}
