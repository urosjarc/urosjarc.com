package gui.domain

data class Odsek(
    val x: Int,
    val y: Int,
    var anotacije: List<Anotacija>,
    val slika: Slika
) {
    val visina get() = this.slika.img.height.toDouble()
    val sirina get() = this.slika.img.width.toDouble()
}
