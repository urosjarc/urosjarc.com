package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

@Serializable
data class Oseba(
    @Contextual
    override val id: Id<Oseba> = Id.new(),
    val ime: String,
    val priimek: String,
    val username: String,
    val tip: Tip,
) : Entiteta<Oseba>() {
    enum class Tip { UCENEC, UCITELJ, INSTRUKTOR, ADMIN }
}

@Serializable
data class Kontakt(
    @Contextual
    override val id: Id<Kontakt> = Id.new(),
    @Contextual
    val oseba_id: Id<Oseba>,
    val data: String,
    val tip: Tip
) : Entiteta<Kontakt>() {
    enum class Tip { EMAIL, TELEFON }
}

@Serializable
data class Naslov(
    @Contextual
    override val id: Id<Naslov> = Id.new(),
    @Contextual
    val oseba_id: Id<Oseba>,
    val drzava: String,
    val mesto: String,
    val ulica: String,
    val zip: Int,
    val dodatno: String
) : Entiteta<Naslov>()

@Serializable
data class Sporocilo(
    @Contextual
    override val id: Id<Sporocilo> = Id.new(),
    @Contextual
    val posiljatelj_id: Id<Kontakt>,
    @Contextual
    val prejemnik_id: Id<Kontakt>,
    val vsebina: String,
) : Entiteta<Sporocilo>()
