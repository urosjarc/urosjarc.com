package si.programerski_klub.server.core.domain.napredovanje

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta

@Serializable
data class Postaja(
    @Contextual
    @SerialName("_id")
    override val id: Id<Postaja> = newId(),
    val naslov: String,
    val opis: String = "opis",
    val vsebina: String = "vsebina",
    @Contextual var vaje: Id<Test>? = null,
    @Contextual var test: Id<Test>? = null,
    @Contextual var stars: Id<Postaja>?,
    var otroci: MutableSet<@Contextual Id<Postaja>> = mutableSetOf(),
    var ponudbe: MutableSet<@Contextual Id<Naloga>> = mutableSetOf()
) : UnikatnaEntiteta<Postaja>() {
    fun povezi(postaja: Postaja) {
        postaja.stars = this.id
        this.otroci.add(postaja.id)
    }
}
