package si.urosjarc.server.api.response

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class TestUpdateReq(
    val datum: LocalDate,
)
