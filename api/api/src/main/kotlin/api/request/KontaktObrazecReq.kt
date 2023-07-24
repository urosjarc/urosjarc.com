package si.urosjarc.server.api.response

import kotlinx.serialization.Serializable

@Serializable
data class KontaktObrazecReq(
    val ime_priimek: String,
    val email: String,
    val telefon: String,
    val vsebina: String,
)
