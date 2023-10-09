package si.urosjarc.server.api.response

import core.base.Encrypted
import core.base.Hashed
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class PrijavaReq(
    @Contextual val username: Encrypted,
    @Contextual val geslo: Hashed,
)
