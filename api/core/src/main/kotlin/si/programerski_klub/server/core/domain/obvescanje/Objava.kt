package si.programerski_klub.server.core.domain.obvescanje

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta

@Serializable
data class Objava(
    @Contextual
    @SerialName("_id")
    override val id: Id<Objava> = newId(),
    val tip: Tip,
    val naslov: String,
    val opis: String,
    val vsebina: String,
) : UnikatnaEntiteta<Objava>() {
    enum class Tip { ZAPOSLOVANJE, NOTRANJA, ZUNANJA }

    override fun enak(entiteta: Objava): Boolean {
        return this.tip == entiteta.tip && this.naslov == entiteta.naslov
    }
}
