package data.extends

import data.Piksel
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.round

fun BufferedImage.dobiPiksel(x: Int, y: Int): Piksel {
    val value = this.getRGB(x, y)
    val r = value and 0x00ff0000 shr 16
    val g = value and 0x0000ff00 shr 8
    val b = value and 0x000000ff

    val hsv = FloatArray(3);
    Color.RGBtoHSB(r, g, b, hsv)

    return Piksel(
        x = x, y = y,
        r = r, g = g, b = b,
        h = round(hsv[0] * 255).toInt(),
        s = round(hsv[1] * 255).toInt(),
        v = round(hsv[2] * 255).toInt()
    )
}

fun BufferedImage.dobiPrvega(y: Int, xMax: Int, check: (Piksel) -> Boolean): Int {
    for (x in 0..xMax) {
        val piksel = this.dobiPiksel(x, y)
        if (check(piksel)) return x
    }
    return -1
}

fun BufferedImage.dobiZadnjega(y: Int, xMax: Int, check: (Piksel) -> Boolean): Int {
    for (x in this.width - 1 downTo this.width - xMax) {
        val piksel = this.dobiPiksel(x, y)
        if (check(piksel)) return x
    }
    return -1
}
