package si.urosjarc.server.core.domain

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Zvezek(
    @BsonId
    override var id: ObjectId? = null,
    val tip: Tip,
    val naslov: String,
) : Entiteta<Zvezek>() {
    enum class Tip { DELOVNI, TEORETSKI }
}

data class Naloga(
    @BsonId
    override var id: ObjectId? = null,
    val tematika_id: String,
    val resitev: String,
    val vsebina: String,
) : Entiteta<Naloga>()


data class Tematika(
    @BsonId
    override var id: ObjectId? = null,
    val naslov: String,
    val zvezek_id: String
) : Entiteta<Tematika>()
