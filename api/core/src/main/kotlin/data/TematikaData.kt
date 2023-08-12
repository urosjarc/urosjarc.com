package data

import domain.Tematika
import domain.Zvezek
import kotlinx.serialization.Serializable

@Serializable
data class TematikaData(
    val tematika: Tematika,
    val zvezek_refs: List<ZvezekData> = listOf(),
)
