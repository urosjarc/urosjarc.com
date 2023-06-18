package si.programerski_klub.server.core.domain.napredovanje

import kotlinx.datetime.DateTimePeriod
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta
import kotlin.math.roundToInt

@Serializable
data class Naloga(
    @Contextual
    @SerialName("_id")
    override val id: Id<Naloga> = newId(),
    val ime: String,
    val opis: String,
    val stevilo_algoritmov: Int,
    val tezavnost_algoritmov: Tezavnost,
    val tezavnost_struktur: Tezavnost,
    val jeziki: MutableSet<Jezik>,
    val resitev: String,
) : UnikatnaEntiteta<Naloga>() {
    enum class Tezavnost { PREPROSTA, LAHKA, NORMALNA, TEZKA, NEMOGOCA }
    enum class Jezik { C, CPP, JAVA, KOTLIN, PYTHON, JAVASCRIPT, BASH }

    override fun enak(entiteta: Naloga): Boolean {
        return this.ime == entiteta.ime
    }

    fun cas_resevanja(): DateTimePeriod = DateTimePeriod(
        minutes = (0.01f * this.stevilo_algoritmov * this.tezavnost_algoritmov.ordinal * this.tezavnost_struktur.ordinal).roundToInt()
    )


}
