package gui.domain

import gui.extend.matrika
import kotlinx.serialization.Serializable

@Serializable
data class Odsek(
    val okvir: Okvir,
    val anotacije: Set<Anotacija>,
    val tip: Tip,
    val dodatno: Set<Okvir> = setOf(),
    val pododseki: List<Odsek> = listOf()
) {
    val tekst: String
        get() {
            val anotacije = this.pododseki.firstOrNull()?.anotacije ?: this.anotacije
            return anotacije.matrika.map { it.map { it.text }.joinToString(separator = " ") }.joinToString(separator = " ")
        }

    enum class Tip {
        NEZNANO,
        GLAVA,
        NALOGA,
        TEORIJA,
        PODNALOGA,
        NASLOV
    }
}
