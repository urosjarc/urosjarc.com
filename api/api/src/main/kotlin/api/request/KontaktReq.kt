package si.urosjarc.server.api.response

import kotlinx.serialization.Serializable

@Serializable
data class KontaktReq(
    val ime_priimek: String,
    val email: String,
    val telefon: String,
    val vsebina: String,
)
