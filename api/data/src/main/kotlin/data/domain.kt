package data

data class Vector<T>(
    val x: T,
    val y: T,
)

data class MinMax<T>(
    val min: T,
    val max: T,
)

data class Pixel(
    val r: Int,
    val g: Int,
    val b: Int,

    val h: Float,
    val s: Float,
    val v: Float,
) {
    fun is_color(): Boolean {
        return s >= 0.1 && v > 0.3
    }

    fun is_white(): Boolean {
        return (s < 0.1) && (v > 0.5)
    }

    fun is_black(): Boolean {
        return (v < 0.15)
    }

    fun is_red_faint(): Boolean {
        return (h > 150 || h < 60) && (s > 0.001 && s < 0.1) && v > 0.3
    }

}

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
