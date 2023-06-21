package si.urosjarc.server.core.domain.uporabniki

import si.urosjarc.server.core.base.Entiteta

data class Oseba(
    val ime: String,
    val priimek: String,
    val username: String,
    val naslov: Naslov,
    val tip: Tip,
) : Entiteta<Oseba>() {
    enum class Tip { UCENEC, ADMIN }
}
