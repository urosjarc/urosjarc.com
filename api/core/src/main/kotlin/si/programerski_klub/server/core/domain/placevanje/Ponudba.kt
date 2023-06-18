package si.programerski_klub.server.core.domain.placevanje

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta

@Serializable
data class Ponudba(
    @Contextual
    @SerialName("_id")
    override val id: Id<Ponudba> = newId(),
    val ime: String,
    val opis: String,
    val slika: String,
    var produkti: MutableSet<@Contextual Id<Produkt>>,
    val tip: Tip
) : UnikatnaEntiteta<Ponudba>() {
    enum class Tip { NAROCNINA, TECAJ, PRODUKT, PROGRAM, CLANARINA }
}
