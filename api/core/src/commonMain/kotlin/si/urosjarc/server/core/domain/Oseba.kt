package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Oseba(
    @Contextual override var _id: String? = null,
    val ime: String,
    val priimek: String,
    val username: String,
    val tip: Tip,
) : Entiteta {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN }
}

@Serializable
data class Kontakt(
    @Contextual override var _id: String? = null,
    @Contextual var oseba_id: String? = null,
    val data: String,
    val tip: Tip
) : Entiteta {
    enum class Tip { EMAIL, TELEFON }
}

@Serializable
data class Naslov(
    @Contextual override var _id: String? = null,
    @Contextual var oseba_id: String? = null,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta

@Serializable
data class Sporocilo(
    @Contextual override var _id: String? = null,
    @Contextual var kontakt_posiljatelj_id: String? = null,
    @Contextual var kontakt_prejemnik_id: String? = null,
    val vsebina: String,
) : Entiteta
