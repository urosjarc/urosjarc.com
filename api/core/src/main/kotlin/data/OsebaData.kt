package data

import domain.Naslov
import domain.Oseba
import kotlinx.serialization.Serializable

@Serializable
data class OsebaData(
    val oseba: Oseba,
    val naslov_refs: List<Naslov> = listOf(),
    val kontakt_refs: List<KontaktData> = listOf(),
)

@Serializable
data class UcenecData(
    val oseba: Oseba,
    val naslov_refs: List<Naslov> = listOf(),
    val kontakt_refs: List<KontaktData> = listOf(),
    val ucenje_ucenec_refs: List<UcenjeData> = listOf(),
    val test_ucenec_refs: List<TestData> = listOf(),
)

@Serializable
data class AdminData(
    val oseba: Oseba,
    val naslov_refs: List<Naslov> = listOf(),
    val kontakt_refs: List<KontaktData> = listOf()
)
