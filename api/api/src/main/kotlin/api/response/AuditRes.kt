package api.response

import domain.Audit
import domain.Status
import domain.Test
import kotlinx.serialization.Serializable

@Serializable
data class AuditRes(
    val status: Status? = null,
    val test: Test? = null,
    val audit: Audit
)
