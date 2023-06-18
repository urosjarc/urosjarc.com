package si.programerski_klub.server.core.domain.uprava

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta


@Serializable
data class Klub(
    @Contextual
    @SerialName("_id")
    override val id: Id<Klub> = newId(),
    val ime: String,
    val tip: Tip,
    val maticna_st: String,
    val davcna_st: String,
    val naslov: Naslov,
    val logo: String,
    val domena: String,
    val app_url: String,
    val api_url: String,
    val oddelki: MutableSet<Oddelek>,
    override var kontakti: MutableSet<Kontakt>,

    ) : UnikatnaEntiteta.ZKontakti<Klub>() {

    enum class Tip { HQ, PODRUZNICA }

    override fun enak(entiteta: Klub): Boolean {
        return this.ime == entiteta.ime
    }

    fun tip_kontaktov() {
        TODO("Not yet implemented")
    }
}
