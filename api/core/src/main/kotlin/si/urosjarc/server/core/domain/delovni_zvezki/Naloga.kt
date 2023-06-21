package si.urosjarc.server.core.domain.delovni_zvezki

import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.uporabniki.Oseba

data class Naloga(
    val id_tematika: Id<Tematika>,
    val resitev: String,
    val vsebina: String
) : Entiteta<Naloga>() {

    data class Status(
        val tip: Tip,
        val id_oseba: Id<Oseba>,
    ) : Entiteta<Status>() {
        enum class Tip { USPEH, NEUSPEH }
    }
}
