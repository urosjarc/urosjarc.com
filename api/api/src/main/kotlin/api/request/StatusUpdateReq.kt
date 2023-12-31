package si.urosjarc.server.api.response

import core.domain.Status
import kotlinx.serialization.Serializable

@Serializable
data class StatusUpdateReq(
    val tip: Status.Tip,
    val sekund: Int,
)
