@file:OptIn(ExperimentalJsExport::class)

import data.TestData
import domain.Status
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.time.Duration

@JsExport
fun trajanje_minut(trajanje: String): Int = Duration.parse(trajanje).inWholeMinutes.toInt()

@JsExport
fun opravljeno(testData: TestData): Float {
    if (testData.status_refs.isNotEmpty()) {
        return testData
            .status_refs.count {
                it.status.tip != null && it.status.tip.toString() == Status.Tip.PRAVILNO.name
            }.toFloat() / testData.status_refs.size
    }
    return 0f
}
