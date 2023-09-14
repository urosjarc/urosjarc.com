package core.data

import core.domain.Naloga
import kotlinx.serialization.Serializable

@Serializable
data class NalogaData(
    val naloga: Naloga,
    val tematika_refs: List<TematikaData> = listOf(),
)
