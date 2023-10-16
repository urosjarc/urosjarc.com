package data.extend

fun Double.vmes(start: Int, end: Int): Boolean {
    return start <= this && end <= this
}
