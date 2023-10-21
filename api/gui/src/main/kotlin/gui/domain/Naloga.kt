package gui.domain

data class Naloga(
    val odsek: Odsek,
    val deli: MutableList<DelNaloge> = mutableListOf(),
) {
    enum class Tip {
        NEZNANO,
        BESEDILO,
        PODNALOGA,
    }
}
