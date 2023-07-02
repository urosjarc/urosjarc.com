package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Id

@Serializable
data class Zvezek(
    @SerialName("_id")
    @Contextual override var id: Id<Zvezek> = Id(),
    val tip: Tip,
    val naslov: String,
) : Entiteta<Zvezek>() {
    enum class Tip { DELOVNI, TEORETSKI }
}

@Serializable
data class Naloga(
    @SerialName("_id")
    @Contextual override var id: Id<Naloga> = Id(),
    @Contextual val tematika_id: Id<Tematika>,
    val resitev: String,
    val vsebina: String,
) : Entiteta<Naloga>()


@Serializable
data class Tematika(
    @SerialName("_id")
    @Contextual override var id: Id<Tematika> = Id(),
    @Contextual val zvezek_id: Id<Zvezek>,
    val naslov: String,
) : Entiteta<Tematika>()
