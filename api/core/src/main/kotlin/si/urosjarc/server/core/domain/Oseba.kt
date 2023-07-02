package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Id

@Serializable
data class Oseba(
    @SerialName("_id")
    @Contextual override var id: Id<Oseba> = Id(),
    val ime: String,
    val priimek: String,
    val username: String,
    val tip: Tip,
) : Entiteta<Oseba>() {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN }
}

@Serializable
data class Kontakt(
    @SerialName("_id")
    @Contextual override var id: Id<Kontakt> = Id(),
    @Contextual val oseba_id: Id<Oseba>,
    val data: String,
    val tip: Tip
) : Entiteta<Kontakt>() {
    enum class Tip { EMAIL, TELEFON }
}

@Serializable
data class Naslov(
    @SerialName("_id")
    @Contextual override var id: Id<Naslov> = Id(),
    @Contextual var oseba_id: Id<Oseba>,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta<Naslov>()

@Serializable
data class Sporocilo(
    @SerialName("_id")
    @Contextual override var id: Id<Sporocilo> = Id(),
    @Contextual val posiljatelj_id: Id<Kontakt>,
    @Contextual val prejemnik_id: Id<Kontakt>,
    val vsebina: String,
) : Entiteta<Sporocilo>()
