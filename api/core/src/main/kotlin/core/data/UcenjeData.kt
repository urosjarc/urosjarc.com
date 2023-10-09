package core.data

import core.domain.Ucenje
import kotlinx.serialization.Serializable

@Serializable
data class UcenjeData(
    val ucenje: Ucenje,
    val oseba_refs: List<OsebaData> = listOf(),
    val ucenje_ucitelj_refs: List<UcenjeData> = listOf(),
)
