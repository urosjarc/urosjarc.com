package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Zvezek(
    @BsonId @Contextual
    override var id: ObjectId? = null,
    val tip: Tip,
    val naslov: String,
) : Entiteta<Zvezek>() {
    enum class Tip { DELOVNI, TEORETSKI }
}

@Serializable
data class Naloga(
    @BsonId @Contextual
    override var id: ObjectId? = null,
    val tematika_id: String,
    val resitev: String,
    val vsebina: String,
) : Entiteta<Naloga>()


@Serializable
data class Tematika(
    @BsonId @Contextual
    override var id: ObjectId? = null,
    val naslov: String,
    val zvezek_id: String
) : Entiteta<Tematika>()
