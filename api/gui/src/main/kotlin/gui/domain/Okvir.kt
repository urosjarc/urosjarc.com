package gui.domain

data class Okvir(
    var x0: Int,
    var x1: Int,
    var y0: Int,
    var y1: Int,
) {
    val sirina: Int get() = x1 - x0
    val visina: Int get() = y1 - y0
}
