package data

import domain.Status
import domain.Test
import kotlinx.serialization.Serializable

@Serializable
data class TestData(
    val test: Test,
    val status_refs: List<Status> = listOf(),
    val naloga_refs: List<NalogaData> = listOf()
)
