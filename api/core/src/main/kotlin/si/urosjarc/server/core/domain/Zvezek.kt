package si.urosjarc.server.core.domain

import kotlinx.datetime.LocalDate
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

class Zvezek(
    override val id: Id<Zvezek> = Id(),
    val tip: Tip,
    val naslov: String,
) : Entiteta<Zvezek>(id = id) {
    enum class Tip { DELOVNI, TEORETSKI }
}

data class Naloga(
    override val id: Id<Naloga> = Id(),
    val id_tematika: Id<Tematika>,
    val resitev: String,
    val vsebina: String,
) : Entiteta<Naloga>(id = id)


data class Tematika(
    override val id: Id<Tematika> = Id(),
    val naslov: String,
    val id_zvezek: Id<Zvezek>
) : Entiteta<Tematika>(id = id)
