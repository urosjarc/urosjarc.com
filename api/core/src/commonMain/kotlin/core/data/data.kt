@file:OptIn(ExperimentalJsExport::class)

package core.data

import core.domain.*
import kotlinx.serialization.Serializable
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
    val naloga_refs: Array<NalogaData> = arrayOf(),
)

@JsExport
@Serializable
data class NalogaData(
    val naloga: Naloga,
    val tematika_refs: Array<Tematika> = arrayOf(),
)

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
