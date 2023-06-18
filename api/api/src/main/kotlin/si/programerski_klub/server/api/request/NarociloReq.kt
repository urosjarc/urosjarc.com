package si.programerski_klub.server.api.request

import kotlinx.serialization.Serializable
import si.programerski_klub.server.core.domain.uprava.Oseba

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
