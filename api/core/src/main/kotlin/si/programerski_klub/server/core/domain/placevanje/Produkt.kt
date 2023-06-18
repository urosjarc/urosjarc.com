package si.programerski_klub.server.core.domain.placevanje

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta

@Serializable
data class Produkt(
    @Contextual
    @SerialName("_id")
    override val id: Id<Produkt> = newId(),
    val tip: Tip,
    val ime: String,
    val opis: String,
    val slika: String,
    val status: Status,
    val lastnosti: MutableMap<String, String>,
    val cena: Float,
    val davek: Float,
    val popust: Float,
    val valuta: Valuta = Valuta.EURO,
    val enota: Enota,
    val zaloga: Int
) : UnikatnaEntiteta<Produkt>() {
    enum class Tip { NAROCNINA, TECAJ, PRODUKT, PROGRAM, CLANARINA }
    enum class Status { KMALU_NA_VOLJO, NA_VOLJO, ZMANJKALO, NI_NA_VOLJO }
    enum class Enota { PREDMET, MINUTA, SEKUNDA, URA }
    enum class Valuta { EURO }
}
