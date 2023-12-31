package core.data

import core.domain.Status
import kotlinx.serialization.Serializable

@Serializable
data class StatusData(
    val status: Status,
    val naloga_refs: List<NalogaData> = listOf(),
)
