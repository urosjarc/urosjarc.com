package si.urosjarc.server.core.domain

import kotlinx.serialization.Contextual

data class Audit(
    @Contextual override var _id: String? = null,
    @Contextual var entiteta_id: String? = null,
    val opis: String,
    val entiteta: String
) : Entiteta
