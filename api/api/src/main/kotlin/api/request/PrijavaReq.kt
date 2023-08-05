package si.urosjarc.server.api.response

import base.Hashed
import kotlinx.serialization.Serializable

@Serializable
data class PrijavaReq(
    val username: Hashed,
    val geslo: Hashed
)
