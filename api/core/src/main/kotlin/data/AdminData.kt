package data

import domain.Naslov
import domain.Oseba
import kotlinx.serialization.Serializable

@Serializable
data class AdminData(
    val oseba: Oseba,
    val naslov_refs: List<NaslovData> = listOf(),
    val kontakt_refs: List<KontaktData> = listOf()
)
