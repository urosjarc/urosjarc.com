package si.urosjarc.server.api.response

import base.Encrypted
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class KontaktObrazecReq(
    @Contextual val ime_priimek: Encrypted,
    @Contextual val email: Encrypted,
    @Contextual val telefon: Encrypted,
    @Contextual val vsebina: Encrypted,
)
