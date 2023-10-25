package gui.domain

import kotlinx.serialization.Serializable

@Serializable
data class Anotacija(
    val okvir: Okvir,
    val text: String,
    var tip: Tip,
) {
    enum class Tip {
        NEZNANO,
        GLAVA,
        NASLOV,
        TEORIJA,
        NALOGA,
        PODNALOGA,
        NOGA,
        DODATNO,
    }

    val prvaCrka: Char get() = this.text.first()

}
