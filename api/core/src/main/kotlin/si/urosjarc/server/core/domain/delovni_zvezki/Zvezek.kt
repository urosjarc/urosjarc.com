package si.urosjarc.server.core.domain.delovni_zvezki

import kotlinx.datetime.LocalDate
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.uporabniki.Oseba

data class Zvezek(
    val id_oseba: Id<Oseba>,
    val tip: Tip,
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate
) : Entiteta<Zvezek>() {
    enum class Tip { DELOVNI, TEORETSKI }
}
