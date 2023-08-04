package domain

import base.Encrypted
import base.Id
import extend.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Sporocilo(
    override var _id: Id<Sporocilo> = Id(),
    var kontakt_posiljatelj_id: Id<Kontakt>,
    var kontakt_prejemnik_id: MutableSet<Id<Kontakt>>,
    @Encrypted var poslano: LocalDateTime = LocalDateTime.zdaj(),
    @Encrypted val vsebina: String,
) : Entiteta<Sporocilo>
