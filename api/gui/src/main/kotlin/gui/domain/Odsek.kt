package gui.domain

data class Odsek(
    val x: Int,
    val y: Int,
    val slika: Slika,
    val tip: Tip,
    var anotacije: List<Anotacija>,
    val deli: MutableList<DelOdseka> = mutableListOf(),
) {
    enum class Tip {
        GLAVA,
        TEORIJA,
        NALOGA,
    }
    val visina get() = this.slika.img.height.toDouble()
    val sirina get() = this.slika.img.width.toDouble()
}
