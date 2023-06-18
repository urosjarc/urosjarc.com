package si.programerski_klub.server.core.domain.placevanje

import kotlinx.serialization.Serializable
import si.programerski_klub.server.core.base.Entiteta

@Serializable
data class Izbira(
    val produkt: Produkt,
    val kolicina: Int,
) : Entiteta<Izbira>
