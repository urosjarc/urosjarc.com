package si.programerski_klub.server.core.domain.placevanje

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta

@Serializable
data class BancniRacun(
    @Contextual
    @SerialName("_id")
    override val id: Id<BancniRacun> = newId(),
    val ime: String,
    val IBAN: String,
    val BIC: String,
    val transakcije: List<Transakcija>
) : UnikatnaEntiteta<BancniRacun>() {

    fun stanje(): Float {
        var vsota = 0f
        for (t in this.transakcije) {
            val predznak = if (t.tip == Transakcija.Tip.ODHODEK) -1 else 1
            vsota += predznak * t.placano
        }
        return vsota
    }

    fun dolgovi(tip_transakcije: Transakcija.Tip): List<Transakcija> {
        val dolgovi: MutableList<Transakcija> = mutableListOf()
        for (t in this.transakcije) {
            if (t.tip == tip_transakcije && t.placano < t.znesek) {
                dolgovi.add(t)
            }
        }
        return dolgovi
    }

    override fun enak(entiteta: BancniRacun): Boolean = this.IBAN == entiteta.IBAN


}
