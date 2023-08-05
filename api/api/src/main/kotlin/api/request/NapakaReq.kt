package api.request

import base.Encrypted
import domain.Napaka
import kotlinx.serialization.Serializable

@Serializable
data class NapakaReq(
    val vsebina: Encrypted,
    val tip: Napaka.Tip
)
