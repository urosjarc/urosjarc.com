package data.domain

data class BoundBox(
    var x0: Int,
    var x1: Int,
    var y0: Int,
    var y1: Int,
) {
    fun width(): Int {
        return x1 - x0
    }

    fun height(): Int {
        return y1 - y0
    }
}
