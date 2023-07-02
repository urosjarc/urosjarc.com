package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import si.urosjarc.server.core.base.Id

@Serializable
data class Zvezek(
    @BsonId @Contextual override var id: Id<Zvezek> = Id(),
    val tip: Tip,
    val naslov: String,
) : Entiteta<Zvezek>() {
    enum class Tip { DELOVNI, TEORETSKI }
}

@Serializable
data class Naloga(
    @BsonId @Contextual override var id: Id<Naloga> = Id(),
    @BsonId @Contextual val tematika_id: Id<Tematika>,
    val resitev: String,
    val vsebina: String,
) : Entiteta<Naloga>()


@Serializable
data class Tematika(
    @BsonId @Contextual override var id: Id<Tematika> = Id(),
    @BsonId @Contextual val zvezek_id: Id<Zvezek>,
    val naslov: String,
) : Entiteta<Tematika>()
