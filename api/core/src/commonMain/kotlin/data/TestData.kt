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
    val status_refs: Array<StatusData> = arrayOf()
) {
    companion object {
        fun opravljeno(testData: TestData): Float {
            if (testData.status_refs.isNotEmpty()) {
                return testData
                    .status_refs.count {
                        it.status.tip != null && it.status.tip.toString() == Status.Tip.PRAVILNO.name
                    }.toFloat() / testData.status_refs.size
            }
            return 0f
        }
    }
}

@JsExport
@Serializable
data class StatusData(
    val status: Status,
    val naloga_refs: Array<ZvezekData> = arrayOf(),
)
