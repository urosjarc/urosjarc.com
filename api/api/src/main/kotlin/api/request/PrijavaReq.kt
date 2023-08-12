package si.urosjarc.server.api.response

import base.Encrypted
import base.Hashed
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class PrijavaReq(
    @Contextual val username: Encrypted,
    @Contextual val geslo: Hashed
)
