package si.programerski_klub.server.core.domain.uprava

import kotlinx.serialization.Serializable
import si.programerski_klub.server.core.base.Entiteta

@Serializable
data class Oddelek(
    val ime: String,
    val opis: String,
    val ekipe: MutableSet<Ekipa>
) : Entiteta<Oddelek> {
    override fun enak(entiteta: Oddelek): Boolean {
        return this.ime == entiteta.ime
    }
}
