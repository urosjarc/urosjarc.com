package si.programerski_klub.server.core.domain.placevanje

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta
import si.programerski_klub.server.core.domain.uprava.Oseba

@Serializable
data class Narocnina(
    @Contextual
    @SerialName("_id")
    override val id: Id<Narocnina> = newId(),
    val produkt: Produkt,
    @Contextual val id_narocila: Id<Narocilo>,
    @Contextual val id_placnika: Id<Oseba>,
    @Contextual val id_uporabnika: Id<Oseba>,
    val frekvenca_racunov: DatePeriod,
    val zacetki: Set<LocalDateTime>,
    val konci: Set<LocalDateTime>,
    val status: Status,
) : UnikatnaEntiteta<Narocnina>() {
    enum class Status { AKTIVNA, V_MIROVANJU, USTAVLJENA }
}
