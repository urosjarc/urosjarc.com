package gui.domain

data class Naloga(
    val odsek: Odsek,
    val glava: MutableList<Anotacija> = mutableListOf(),
    val podnaloge: MutableList<Anotacija> = mutableListOf(),
) {
    enum class Tip {
        NEZNANO,
        BESEDILO,
        PODNALOGA,
    }
}
