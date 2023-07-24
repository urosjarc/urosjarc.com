package api.response

import domain.Kontakt
import domain.Oseba
import domain.Sporocilo
import kotlinx.serialization.Serializable

@Serializable
data class KontaktObrazecRes(
    val oseba: Oseba,
    val telefon: Kontakt,
    val email: Kontakt,
    val sporocila: List<Sporocilo>
)
