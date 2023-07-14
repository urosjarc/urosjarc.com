package data

import domain.Naloga
import domain.Tematika
import kotlinx.serialization.Serializable

@Serializable
data class ZvezekData(
    val naloga: Naloga,
    val tematika_refs: List<Tematika> = listOf(),
)
