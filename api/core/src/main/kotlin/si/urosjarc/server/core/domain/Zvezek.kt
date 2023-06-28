package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

@Serializable
data class Zvezek(
    @Contextual
    override val id: Id<Zvezek> = Id.new(),
    val tip: Tip,
    val naslov: String,
) : Entiteta<Zvezek>() {
    enum class Tip { DELOVNI, TEORETSKI }
}

@Serializable
data class Naloga(
    @Contextual
    override val id: Id<Naloga> = Id.new(),
    @Contextual
    val tematika_id: Id<Tematika>,
    val resitev: String,
    val vsebina: String,
) : Entiteta<Naloga>()


@Serializable
data class Tematika(
    @Contextual
    override val id: Id<Tematika> = Id.new(),
    val naslov: String,
    @Contextual
    val zvezek_id: Id<Zvezek>
) : Entiteta<Tematika>()
