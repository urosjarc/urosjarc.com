package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Oseba(
    @Contextual override var _id: ObjectId? = null,
    val ime: String,
    val priimek: String,
    val username: String,
    val tip: Tip,
) : Entiteta {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN }
}

@Serializable
data class Kontakt(
    @Contextual override var _id: ObjectId? = null,
    @Contextual var oseba_id: ObjectId? = null,
    val data: String,
    val tip: Tip
) : Entiteta {
    enum class Tip { EMAIL, TELEFON }
}

@Serializable
data class Naslov(
    @Contextual override var _id: ObjectId? = null,
    @Contextual var oseba_id: ObjectId? = null,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta

@Serializable
data class Sporocilo(
    @Contextual override var _id: ObjectId? = null,
    @Contextual var oseba_posiljatelj_id: ObjectId? = null,
    @Contextual var oseba_prejemnik_id: ObjectId? = null,
    val vsebina: String,
) : Entiteta
