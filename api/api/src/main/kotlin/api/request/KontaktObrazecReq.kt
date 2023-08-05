package si.urosjarc.server.api.response

import base.Encrypted
import kotlinx.serialization.Serializable

@Serializable
data class KontaktObrazecReq(
    val ime_priimek: Encrypted,
    val email: Encrypted,
    val telefon: Encrypted,
    val vsebina: Encrypted,
)
