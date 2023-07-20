package api.request

import domain.Napaka
import kotlinx.serialization.Serializable

@Serializable
class NapakaReq(
    val vsebina: String,
    val tip: Napaka.Tip
)
