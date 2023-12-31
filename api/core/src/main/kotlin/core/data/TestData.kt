package core.data

import core.domain.Test
import kotlinx.serialization.Serializable

@Serializable
data class TestData(
    val test: Test,
    val status_refs: List<StatusData> = listOf(),
    val naloga_refs: List<NalogaData> = listOf()
)
