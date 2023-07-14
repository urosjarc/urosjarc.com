package data

import domain.Sporocilo
import kotlinx.serialization.Serializable

@Serializable
data class SporociloData(
    val sporocilo: Sporocilo,
    val kontakt_refs: List<KontaktData> = listOf()
)
