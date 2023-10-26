package gui.domain

import kotlinx.serialization.Serializable

@Serializable
data class Anotacija(
    val okvir: Okvir,
    val text: String,
    var tip: Tip,
) {
    enum class Tip {
        PODNALOGA,
        NALOGA,
        NASLOV,
        TEORIJA,
        NOGA,
        DODATNO,
        NEZNANO,
    }

    val prvaCrka: Char get() = this.text.first()

}
