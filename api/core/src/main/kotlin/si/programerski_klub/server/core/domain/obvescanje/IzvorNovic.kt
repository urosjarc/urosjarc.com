package si.programerski_klub.server.core.domain.obvescanje

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta

@Serializable
data class IzvorNovic(
    @Contextual
    @SerialName("_id")
    override val id: Id<IzvorNovic> = newId(),
    val protokol: Protokol,
    val tip: Tip,
    val naslov: String,
    val izvor: String
) : UnikatnaEntiteta<IzvorNovic>() {
    enum class Protokol { RSS }
    enum class Tip { BLOG, YOUTUBE, HUMOR }

    override fun enak(entiteta: IzvorNovic): Boolean {
        return this.izvor == entiteta.izvor
    }
}
