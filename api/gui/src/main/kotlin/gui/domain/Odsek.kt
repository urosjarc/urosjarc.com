package gui.domain

data class Odsek(
    val tip: Tip,
    val okvir: Okvir,
    var deli: List<Okvir>
) {
    enum class Tip {
        GLAVA,
        TEORIJA,
        NALOGA,
    }
}
