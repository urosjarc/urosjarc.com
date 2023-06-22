package si.urosjarc.server.core.domain

import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

data class Oseba(
    override val id: Id<Oseba> = Id(),
    val ime: String,
    val priimek: String,
    val username: String,
    val tip: Tip,
) : Entiteta<Oseba>(id = id) {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN }
}

data class Kontakt(
    override val id: Id<Kontakt> = Id(),
    val id_oseba: Id<Oseba>,
    val data: String,
    val tip: Tip
) : Entiteta<Kontakt>(id = id) {
    enum class Tip { EMAIL, TELEFON }
}

data class Naslov(
    override val id: Id<Naslov> = Id(),
    val id_oseba: Id<Oseba>,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta<Naslov>(id = id)

data class Sporocilo(
    override val id: Id<Sporocilo> = Id(),
    val id_posiljatelj: Id<Kontakt>,
    val id_prejemnik: Id<Kontakt>,
    val vsebina: String,
) : Entiteta<Sporocilo>(id = id)
