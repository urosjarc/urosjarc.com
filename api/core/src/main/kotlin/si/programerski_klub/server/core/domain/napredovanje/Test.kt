package si.programerski_klub.server.core.domain.napredovanje

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta

@Serializable
data class Test(
    @SerialName("_id")
    @Contextual override val id: Id<Test> = newId(),
    val ime: String,
    val opis: String,
    var naloge: MutableSet<Naloga>,
    val tip: Tip
) : UnikatnaEntiteta<Test>() {
    enum class Tip { TEST, VAJE }
}
