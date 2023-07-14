package si.urosjarc.server.api.response

import kotlinx.serialization.Serializable

@Serializable
data class PrijavaRes(
    val token: String
)
