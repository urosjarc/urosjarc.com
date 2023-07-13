package si.urosjarc.server.api.response

import domain.Status
import kotlinx.serialization.Serializable

@Serializable
data class StatusUpdateReq(
    val tip: Status.Tip
)
