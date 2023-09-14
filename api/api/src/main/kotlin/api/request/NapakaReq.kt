package api.request

import core.base.Encrypted
import core.domain.Napaka
import kotlinx.serialization.Serializable

@Serializable
data class NapakaReq(
    val vsebina: Encrypted,
    val tip: Napaka.Tip,
)
