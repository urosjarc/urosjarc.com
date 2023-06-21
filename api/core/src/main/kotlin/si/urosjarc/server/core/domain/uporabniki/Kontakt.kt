package si.urosjarc.server.core.domain.uporabniki

import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

data class Kontakt(
    val id_oseba: Id<Oseba>,
    val data: String,
    val tip: Tip
) : Entiteta<Kontakt>() {
    enum class Tip { EMAIL, TELEFON }

}
