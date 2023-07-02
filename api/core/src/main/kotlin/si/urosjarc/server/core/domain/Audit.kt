package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import si.urosjarc.server.core.base.Id

data class Audit(
    @SerialName("_id")
    @Contextual override var id: Id<Audit> = Id(),
    @Contextual val entiteta_id: Id<Entiteta<Any>>,
    val opis: String,
    val entiteta: String
) : Entiteta<Audit>()
