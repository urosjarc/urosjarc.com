@file:OptIn(ExperimentalJsExport::class)

package si.urosjarc.server.core.repos

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.domain.*
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class OsebaData(
    val oseba: Oseba,
    val naslov_refs: Array<Naslov> = arrayOf(),
    val ucenje_ucitelj_refs: Array<UcenjeData> = arrayOf(),
    val kontakt_refs: Array<KontaktData> = arrayOf(),
    val test_refs: Array<TestData> = arrayOf()
)


@JsExport
@Serializable
data class TestData(
    val test: Test,
    val status_refs: Array<Status> = arrayOf()
) {
    fun opravljeno(): Float {
        var count = 0f
        for (status in status_refs) {
            if (status.tip == Status.Tip.USPEH) {
                count += 1
            }
        }
        return count / status_refs.size
    }
}

@JsExport
@Serializable
data class KontaktData(
    val kontakt: Kontakt,
    val oseba_refs: Array<Oseba> = arrayOf(),
    val sporocilo_prejemnik_refs: Array<SporociloData> = arrayOf(),
    val sporocilo_posiljatelj_refs: Array<SporociloData> = arrayOf()
)

@JsExport
@Serializable
data class SporociloData(
    val sporocilo: Sporocilo,
    val kontakt_refs: Array<KontaktData> = arrayOf()
)

@JsExport
@Serializable
data class UcenjeData(
    val ucenje: Ucenje,
    val oseba_refs: Array<Oseba> = arrayOf(),
    val ucenje_ucenec_refs: Array<UcenjeData> = arrayOf(),
)
