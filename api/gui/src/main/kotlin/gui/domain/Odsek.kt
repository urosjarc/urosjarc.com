package gui.domain

data class Odsek(
    val okvir: Okvir,
    val anotacije: Set<Anotacija>,
    val tip: Tip,
    val dodatno: Set<Okvir> = setOf(),
    val pododseki: List<Odsek> = listOf()
) {
    enum class Tip {
        NEZNANO,
        GLAVA,
        NALOGA,
        TEORIJA,
        PODNALOGA
    }
}
