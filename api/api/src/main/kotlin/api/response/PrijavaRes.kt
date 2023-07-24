package si.urosjarc.server.api.response

import domain.Oseba
import kotlinx.serialization.Serializable

@Serializable
data class PrijavaRes(
    val token: String,
    val tip: MutableSet<Oseba.Tip>
)
