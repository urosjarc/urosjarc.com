package si.programerski_klub.server.core.domain.placevanje

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import si.programerski_klub.server.core.base.UnikatnaEntiteta
import si.programerski_klub.server.core.domain.uprava.Oseba
import si.programerski_klub.server.core.extend.now

/**
 * Tukaj se morajo podatki shranjevati staticno saj se osebne informacije lahko spreminjajo skozi cas.
 */
@Serializable
data class Narocilo(
    @Contextual
    @SerialName("_id")
    override val id: Id<Narocilo> = newId(),
    val kosarica: Set<Izbira>,
    var placnik: Oseba?,  // - Ce obstaja zraven prejemnika tudi plačnik potem bo naročilo plačal prejemnik.
    var prejemnik: Oseba, // - Ce obstaja samo prejemnik potem bo tudi prejemnik placeval za narocilo.
) : UnikatnaEntiteta<Narocilo>() {

    enum class Status { PREJETO, V_PRIPRAVI, ODPOSLANO, AKTIVNO, ZAKLJUCENO }

    val status = Status.PREJETO
    val ustvarjeno = LocalDateTime.now()


}
