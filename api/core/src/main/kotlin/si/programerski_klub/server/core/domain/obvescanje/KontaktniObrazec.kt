package si.programerski_klub.server.core.domain.obvescanje

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta
import si.programerski_klub.server.core.domain.uprava.Kontakt

@Serializable
data class KontaktniObrazec(
    @Contextual
    @SerialName("_id")
    override val id: Id<KontaktniObrazec> = newId(),
    val ime: String,
    val priimek: String,
    val vsebina: String,
    override var kontakti: MutableSet<Kontakt>
) : UnikatnaEntiteta.ZKontakti<KontaktniObrazec>()
