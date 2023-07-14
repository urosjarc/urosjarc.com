package data

import domain.Oseba
import domain.Ucenje
import kotlinx.serialization.Serializable

@Serializable
data class UcenjeData(
    val ucenje: Ucenje,
    val oseba_refs: List<Oseba> = listOf(),
    val ucenje_ucenec_refs: List<UcenjeData> = listOf(),
)
