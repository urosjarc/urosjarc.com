package gui.extend

fun Double.vmes(start: Int, end: Int): Boolean {
    return start <= this && end >= this
}
fun Double.vmes(start: Double, end: Double): Boolean {
    return start <= this && end >= this
}
