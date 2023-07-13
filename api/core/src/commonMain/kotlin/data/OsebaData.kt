@file:OptIn(ExperimentalJsExport::class)

package data

import domain.Kontakt
import domain.Naslov
import domain.Oseba
import domain.Sporocilo
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
