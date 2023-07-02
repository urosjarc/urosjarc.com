package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Oseba(
    @BsonId @Contextual
    override var id: ObjectId? = null,
    val ime: String,
    val priimek: String,
    val username: String,
    val tip: Tip,
) : Entiteta<Oseba>() {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN }
}

@Serializable
data class Kontakt(
    @BsonId @Contextual
    override var id: ObjectId? = null,
    val oseba_id: String,
    val data: String,
    val tip: Tip
) : Entiteta<Kontakt>() {
    enum class Tip { EMAIL, TELEFON }
}

@Serializable
data class Naslov(
    @BsonId @Contextual
    override var id: ObjectId? = null,
    val oseba_id: String,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta<Naslov>()

@Serializable
data class Sporocilo(
    @BsonId @Contextual
    override var id: ObjectId? = null,
    val posiljatelj_id: String,
    val prejemnik_id: String,
    val vsebina: String,
) : Entiteta<Sporocilo>()
