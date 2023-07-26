package api.request

import domain.Napaka
import kotlinx.serialization.Serializable

@Serializable
data class NapakaReq(
    val vsebina: String,
    val tip: Napaka.Tip
)
