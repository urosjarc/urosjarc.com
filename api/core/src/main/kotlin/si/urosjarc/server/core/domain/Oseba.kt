package si.urosjarc.server.core.domain

import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

data class Oseba(
    val ime: String,
    val priimek: String,
    val username: String,
    val naslov: Naslov,
    val tip: Tip,
) : Entiteta<Oseba>() {
    enum class Tip { UCENEC, ADMIN }
}

data class Kontakt(
    val id_oseba: Id<Oseba>,
    val data: String,
    val tip: Tip
) : Entiteta<Kontakt>() {
    enum class Tip { EMAIL, TELEFON }
}

data class Naslov(
    val id_oseba: Id<Oseba>,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta<Naslov>()

data class Zaznamek(
    val id_oseba: Id<Oseba>,
    val vsebina: String
) : Entiteta<Naslov>()
