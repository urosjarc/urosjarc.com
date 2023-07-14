package data

import domain.Kontakt
import domain.Oseba
import kotlinx.serialization.Serializable

@Serializable
data class KontaktData(
    val kontakt: Kontakt,
    val oseba_refs: List<Oseba> = listOf(),
    val sporocilo_prejemnik_refs: List<SporociloData> = listOf(),
    val sporocilo_posiljatelj_refs: List<SporociloData> = listOf()
)
