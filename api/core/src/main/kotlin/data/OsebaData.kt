package data

import domain.Naslov
import domain.Oseba
import kotlinx.serialization.Serializable

@Serializable
data class OsebaData(
    val oseba: Oseba,
    val naslov_refs: List<Naslov> = listOf(),
    val ucenje_ucenec_refs: List<UcenjeData> = listOf(),
    val kontakt_refs: List<KontaktData> = listOf(),
    val test_ucenec_refs: List<TestData> = listOf(),
)
