package si.urosjarc.server.core.domain

import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

data class Audit(
    override val id: Id<Audit> = Id(),
    val opis: String,
    val id_entiteta: Id<Entiteta<Any>>,
    val entiteta: String
) : Entiteta<Audit>(id = id)
