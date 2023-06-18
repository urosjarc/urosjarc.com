package si.programerski_klub.server.api.request

import kotlinx.serialization.Serializable
import si.programerski_klub.server.core.domain.uprava.Oseba

@Serializable
data class VclanitevReq(
    val clanarina: String,
    val clan: Oseba,
    val skrbnik: Oseba?
)
