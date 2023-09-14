package core.data

import core.domain.Audit
import kotlinx.serialization.Serializable

@Serializable
class AuditData(
    val audit: Audit
)
