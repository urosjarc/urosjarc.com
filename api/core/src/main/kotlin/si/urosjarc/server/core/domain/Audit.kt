package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

@Serializable
data class Audit(
    @Contextual
    override val id: Id<Audit> = Id(),
    val opis: String,
    val entiteta_id: String,
    val entiteta: String
) : Entiteta<Audit>()
