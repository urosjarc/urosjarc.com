package si.urosjarc.server.api.request

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.domain.uprava.Oseba

@Serializable
data class VclanitevReq(
    val clanarina: String,
    val clan: Oseba,
    val skrbnik: Oseba?
)
