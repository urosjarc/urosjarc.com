package si.programerski_klub.server.core.domain.obvescanje

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta
import si.programerski_klub.server.core.domain.uprava.Oseba

@Serializable
data class Dogodek(
    @Contextual
    @SerialName("_id")
    override val id: Id<Dogodek> = newId(),
    val ime: String,
    val opis: String,
    val trajanje: Float,
    val tip: Tip,
    val zacetek: LocalDateTime,
    val konec: LocalDateTime,
    val clani: MutableSet<Oseba>,
) : UnikatnaEntiteta<Dogodek>() {
    enum class Tip { TEKMA, TRENING, SRECANJE, UPRAVNI_ODBOR }

    override fun enak(entiteta: Dogodek): Boolean {
        return this.zacetek == entiteta.zacetek
    }
}
