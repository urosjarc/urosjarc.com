package data

import domain.Naslov
import domain.Oseba
import kotlinx.serialization.Serializable

@Serializable
data class UciteljData(
    val oseba: Oseba,
    val naslov_refs: List<Naslov> = listOf(),
    val kontakt_refs: List<KontaktData> = listOf()
)
