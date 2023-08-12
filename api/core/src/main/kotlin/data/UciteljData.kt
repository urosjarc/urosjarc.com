package data

import domain.Audit
import domain.Naslov
import domain.Oseba
import kotlinx.serialization.Serializable

@Serializable
data class UciteljData(
    val oseba: Oseba,
    val naslov_refs: List<NaslovData> = listOf(),
    val kontakt_refs: List<KontaktData> = listOf(),
    val ucenje_ucitelj_refs: List<UcenjeData> = listOf(),
    val test_admin_refs: List<TestData> = listOf(),
    val audit_refs: List<AuditData> = listOf()
)
