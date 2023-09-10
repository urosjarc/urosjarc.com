package data.app

import java.awt.image.BufferedImage

data class ZipPart(
    val tip: Tip,
    var yStart: Int,
    var yEnd: Int,
    var image: BufferedImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
) {
    enum class Tip { naloga, naslov, teorija, prazno }
}

data class ZipSlika(
    val image: BufferedImage,
    val parts: MutableList<ZipPart> = mutableListOf()
)

data class Pixel(
    val r: Int,
    val g: Int,
    val b: Int,

    val h: Float,
    val s: Float,
    val v: Float
) {
    fun is_red(): Boolean {
        return (h > 300 || h < 60) && s >= 0.1 && v > 0.3
    }

    fun is_white(): Boolean {
        return (s < 0.1) && (v > 0.5)
    }

    fun is_red_faint(): Boolean {
        return (h > 280 || h < 60) && (s > 0.001 && s < 0.1) && v > 0.3
    }

}
