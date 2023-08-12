package data

import domain.Audit
import kotlinx.serialization.Serializable

@Serializable
class AuditData(
    val audit: Audit
)
