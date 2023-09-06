package data

data class Pixel(
    val r: Int,
    val g: Int,
    val b: Int,

    val h: Float,
    val s: Float,
    val v: Float
) {
    fun is_red(): Boolean {
        return (h > 300 || h < 60) && s > 0.005 && v > 0.3
    }
}
