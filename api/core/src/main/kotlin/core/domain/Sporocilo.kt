package core.domain

import core.base.Encrypted
import core.base.Id
import core.extend.zdaj
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Sporocilo(
    override var _id: Id<Sporocilo> = Id(),
    var kontakt_posiljatelj_id: Id<Kontakt>,
    var kontakt_prejemnik_id: MutableSet<Id<Kontakt>>,
    var poslano: LocalDateTime = LocalDateTime.zdaj(),
    @Contextual val vsebina: Encrypted,
) : Entiteta<Sporocilo>
