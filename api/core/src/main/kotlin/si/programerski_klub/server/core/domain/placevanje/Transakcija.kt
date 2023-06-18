package si.programerski_klub.server.core.domain.placevanje

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta


@Serializable
data class Transakcija(
    @Contextual
    @SerialName("_id")
    override val id: Id<Transakcija> = newId(),
    val tip: Tip,
    val kategorija: Kategorija,
    val rok: LocalDateTime,
    val opis: String,
    val znesek: Float,
    val placano: Float = 0f,
) : UnikatnaEntiteta<Transakcija>() {
    enum class Tip { PRIHODEK, ODHODEK }
    enum class Kategorija { CLANARINA, PLACA, STROSKI, OPREMA }

    override fun enak(entiteta: Transakcija): Boolean = this == entiteta

}
