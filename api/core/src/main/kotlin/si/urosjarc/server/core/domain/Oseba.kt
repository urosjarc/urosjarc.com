package si.urosjarc.server.core.domain

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Oseba(
    @BsonId
    override var id: ObjectId? = null,
    val ime: String,
    val priimek: String,
    val username: String,
    val tip: Tip,
) : Entiteta<Oseba>() {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN }
}

data class Kontakt(
    @BsonId
    override var id: ObjectId? = null,
    val oseba_id: String,
    val data: String,
    val tip: Tip
) : Entiteta<Kontakt>() {
    enum class Tip { EMAIL, TELEFON }
}

data class Naslov(
    @BsonId
    override var id: ObjectId? = null,
    val oseba_id: String,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta<Naslov>()

data class Sporocilo(
    @BsonId
    override var id: ObjectId? = null,
    val posiljatelj_id: String,
    val prejemnik_id: String,
    val vsebina: String,
) : Entiteta<Sporocilo>()
