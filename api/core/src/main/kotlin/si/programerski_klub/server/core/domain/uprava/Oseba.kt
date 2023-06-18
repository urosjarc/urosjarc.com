package si.programerski_klub.server.core.domain.uprava

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta

@Serializable
data class Oseba(
    @SerialName("_id")
    @Contextual override val id: Id<Oseba> = newId(),
    val ime: String,
    val priimek: String,
    val rojen: LocalDate,
    val naslov: Naslov,
    var tip: MutableSet<Tip> = mutableSetOf(),
    override var kontakti: MutableSet<Kontakt>,
) : UnikatnaEntiteta.ZKontakti<Oseba>() {

    enum class Tip { CLAN, ADMIN }

    override fun enak(entiteta: Oseba): Boolean = this.ime == entiteta.ime && this.priimek == entiteta.priimek && this.rojen == entiteta.rojen

    fun zdruzi(oseba: Oseba) {
        this.kontakti.addAll(oseba.kontakti)
    }

}
