package gui.domain

data class Piksel(
    val r: Int,
    val g: Int,
    val b: Int,

    val h: Float,
    val s: Float,
    val v: Float,
) {
    companion object {
        fun average(pixels: Collection<Piksel>): Piksel {
            return Piksel(
                r = pixels.map { it.r }.average().toInt(),
                g = pixels.map { it.g }.average().toInt(),
                b = pixels.map { it.b }.average().toInt(),
                h = pixels.map { it.h }.average().toFloat(),
                s = pixels.map { it.s }.average().toFloat(),
                v = pixels.map { it.v }.average().toFloat(),
            )
        }
    }

    fun is_red(): Boolean {
        return (h > 300 || h < 30) && s > 0.1 && v > 0.3
    }

    fun is_color(): Boolean {
        return s >= 0.1 && v > 0.3
    }

    fun is_white(): Boolean {
        return (s < 0.1) && (v > 0.5)
    }

    fun is_black(): Boolean = v < 0.30

    fun is_red_faint(): Boolean {
        return (h > 150 || h < 60) && (s > 0.001 && s < 0.1) && v > 0.3
    }

}
