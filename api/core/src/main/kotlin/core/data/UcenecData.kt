package core.data

import core.domain.Oseba
import kotlinx.serialization.Serializable

@Serializable
data class UcenecData(
    val oseba: Oseba,
    val naslov_refs: List<NaslovData> = listOf(),
    val kontakt_refs: List<KontaktData> = listOf(),
    val ucenje_ucenec_refs: List<UcenjeData> = listOf(),
    val test_ucenec_refs: List<TestData> = listOf(),
    val audit_refs: List<AuditData> = listOf()
)
