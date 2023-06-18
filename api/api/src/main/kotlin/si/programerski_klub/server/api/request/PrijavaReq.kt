package si.programerski_klub.server.api.response

import kotlinx.serialization.Serializable

@Serializable
data class PrijavaReq(
    val username: String,
    val password: String
)
