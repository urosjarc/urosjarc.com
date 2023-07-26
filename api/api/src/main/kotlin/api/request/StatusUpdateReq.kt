package si.urosjarc.server.api.response

import base.Id
import domain.Status
import kotlinx.serialization.Serializable

@Serializable
data class StatusUpdateReq(
    val id: Id<Status>,
    val tip: Status.Tip,
    val sekund: Int
)
