package gui.domain

data class Odsek(
    val anotacije: Set<Anotacija>,
    val okvir: Okvir,
    val tip: Tip,
) {
    enum class Tip {
        NEZNANO,
        GLAVA,
        NALOGA,
        TEORIJA,
    }
}
