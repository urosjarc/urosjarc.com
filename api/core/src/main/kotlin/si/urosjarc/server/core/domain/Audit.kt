package si.urosjarc.server.core.domain

import si.urosjarc.server.core.base.Entiteta
import si.urosjarc.server.core.base.Id

data class Audit(
    val opis: String,
    val id_entiteta: Id<Entiteta<Any>>,
): Entiteta<Audit>()
