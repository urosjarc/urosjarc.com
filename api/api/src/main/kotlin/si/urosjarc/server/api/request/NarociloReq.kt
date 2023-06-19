package si.urosjarc.server.api.request

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.domain.uprava.Oseba

@Serializable
data class NarociloReq(
    val izbire: List<IzbiraReq>,
    val clan: Oseba,
    val skrbnik: Oseba?
) {
    @Serializable
    data class IzbiraReq(
        val kolicina: Int,
        val produkt: String
    )
}
