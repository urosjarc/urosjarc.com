package gui.domain

data class Okvir(
    var x0: Double,
    var x1: Double,
    var y0: Double,
    var y1: Double,
) {
    val sirina: Double get() = x1 - x0
    val visina: Double get() = y1 - y0
}
