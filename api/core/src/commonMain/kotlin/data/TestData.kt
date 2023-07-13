@file:OptIn(ExperimentalJsExport::class)

package data

import domain.Status
import domain.Test
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class TestData(
    val test: Test,
    val status_refs: Array<StatusData> = arrayOf(),
    var opravljeno: Float = 0f
) {
    init {

        if (status_refs.size > 0) {
            this.opravljeno = (this.status_refs
                .count {
                    it.status.tip == Status.Tip.PRAVILNO
                }.toFloat() / status_refs.size)
        }
    }
}

@JsExport
@Serializable
data class StatusData(
    val status: Status,
    val naloga_refs: Array<ZvezekData> = arrayOf(),
)
