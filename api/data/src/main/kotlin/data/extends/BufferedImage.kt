package data.extends

import com.recognition.software.jdeskew.ImageDeskew
import data.Piksel
import net.sourceforge.tess4j.util.ImageHelper
import java.awt.BorderLayout
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import kotlin.math.round

fun BufferedImage.dobiPiksel(x: Int, y: Int): Piksel {
    var value: Int = -1
    try {
        value = this.getRGB(x, y)
    } catch (_: ArrayIndexOutOfBoundsException) {
        throw ArrayIndexOutOfBoundsException("Nedovoljen pixel: 0 <= $x < ${this.width}, 0 <= $y < ${this.height}")
    }
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

fun BufferedImage.dobiPrvega(y: Int, xMin: Int, xMax: Int, check: (Piksel) -> Boolean): Int {
    var count = 0
    for (x in xMin until xMax) {
        val piksel0 = this.dobiPiksel(x, y)
        if (check(piksel0)) {
            count++
            if (count == 3) return x
        } else count = 0
    }
    return -1
}

fun BufferedImage.dobiZadnjega(y: Int, xMin: Int, xMax: Int, check: (Piksel) -> Boolean): Int {
    var count = 0
    for (x in this.width - xMin - 1 downTo this.width - xMax) {
        val piksel0 = this.dobiPiksel(x, y)
        if (check(piksel0)) {
            count++
            if (count == 3) return x
        } else count = 0
    }
    return -1
}

fun BufferedImage.show() {
    val frame = JFrame().apply {
        this.title = "stained_image"
        this.setSize(this.width, this.height)
        this.defaultCloseOperation = javax.swing.WindowConstants.EXIT_ON_CLOSE
    }
    val label = JLabel().apply {
        this.icon = ImageIcon(this@show)
    }
    frame.apply {
        this.contentPane.add(label, BorderLayout.CENTER)
        this.setLocationRelativeTo(null)
        this.pack()
        this.isVisible = true
    }

    label.icon = ImageIcon(this)
}

fun BufferedImage.save(file: File) {
    ImageIO.write(this, "png", file)
}

fun BufferedImage.deskew(): BufferedImage {
    val imgdeskew = ImageDeskew(this) // BufferedImage img
    println("Image rotated: ${imgdeskew.skewAngle} deg")
    return ImageHelper.rotateImage(this, -imgdeskew.skewAngle) // rotateImage static method
}
