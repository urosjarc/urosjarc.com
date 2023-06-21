package si.urosjarc.server.core.domain

import kotlinx.datetime.LocalDate
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

data class Zvezek(
    val tip: Tip,
    val naslov: String,
) : Entiteta<Zvezek>() {
    enum class Tip { DELOVNI, TEORETSKI }
}

data class Naloga(
    val id_tematika: Id<Tematika>,
    val resitev: String,
    val vsebina: String,
) : Entiteta<Naloga>()


data class Tematika(
    val naslov: String,
    val id_zvezek: Id<Zvezek>
) : Entiteta<Tematika>()
