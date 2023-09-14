package core.data

import core.domain.Tematika
import kotlinx.serialization.Serializable

@Serializable
data class TematikaData(
    val tematika: Tematika,
    val zvezek_refs: List<ZvezekData> = listOf(),
)
