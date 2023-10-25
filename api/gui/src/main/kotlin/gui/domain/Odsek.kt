package gui.domain

data class Odsek(
    val anotacije: List<Anotacija>,
    val okvir: Okvir,
    val tip: Tip,
) {
    enum class Tip {
        GLAVA,
        TEORIJA,
        NALOGA,
    }
}
