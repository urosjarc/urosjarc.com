package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Zvezek(
    @SerialName("_id")
    @Contextual override var id: ObjectId? = null,
    val tip: Tip,
    val naslov: String,
) : Entiteta<Zvezek>() {
    enum class Tip { DELOVNI, TEORETSKI }
}

@Serializable
data class Naloga(
    @SerialName("_id")
    @Contextual override var id: ObjectId? = null,
    @Contextual var tematika_id: ObjectId? = null,
    val resitev: String,
    val vsebina: String,
) : Entiteta<Naloga>()


@Serializable
data class Tematika(
    @SerialName("_id")
    @Contextual override var id: ObjectId? = null,
    @Contextual var zvezek_id: ObjectId? = null,
    val naslov: String,
) : Entiteta<Tematika>()
