package api.response

import core.domain.Audit
import core.domain.Status
import core.domain.Test
import kotlinx.serialization.Serializable

@Serializable
data class AuditRes(
    val status: Status? = null,
    val test: Test? = null,
    val audit: Audit,
)
