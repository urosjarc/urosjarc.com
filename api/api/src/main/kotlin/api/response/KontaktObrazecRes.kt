package api.response

import core.domain.Kontakt
import core.domain.Oseba
import core.domain.Sporocilo
import kotlinx.serialization.Serializable

@Serializable
data class KontaktObrazecRes(
    val oseba: Oseba,
    val telefon: Kontakt,
    val email: Kontakt,
    val sporocila: List<Sporocilo>,
)
