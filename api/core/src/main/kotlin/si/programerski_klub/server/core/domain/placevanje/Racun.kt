package si.programerski_klub.server.core.domain.placevanje

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta
import si.programerski_klub.server.core.domain.uprava.Klub

@Serializable
data class Racun(
    @Contextual
    @SerialName("_id")
    override val id: Id<Racun> = newId(),
    val klub: Klub,
    val stevilka: String,
    val bancni_racun: BancniRacun,
    val sklic: String,
    val narocila: Set<Narocilo>,
) : UnikatnaEntiteta<Racun>() {

}
