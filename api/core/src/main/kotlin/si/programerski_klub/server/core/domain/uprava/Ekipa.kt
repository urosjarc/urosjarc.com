package si.programerski_klub.server.core.domain.uprava

import kotlinx.serialization.Serializable
import si.programerski_klub.server.core.base.Entiteta

@Serializable
data class Ekipa(
    val ime: String,
    val opis: String,
    val oddelki: MutableSet<Oddelek>
) : Entiteta<Ekipa>
