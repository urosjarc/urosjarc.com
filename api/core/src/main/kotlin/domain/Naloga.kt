package domain

import base.Encrypted
import base.Id
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Naloga(
    override var _id: Id<Naloga> = Id(),
    var tematika_id: Id<Tematika>,
    @Contextual val resitev_img: Encrypted,
    @Contextual val resitev_txt: Encrypted,
    @Contextual val vsebina_img: Encrypted,
    @Contextual val vsebina_txt: Encrypted,
) : Entiteta<Naloga>
