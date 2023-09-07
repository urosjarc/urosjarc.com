package data

import com.recognition.software.jdeskew.ImageDeskew
import net.coobird.thumbnailator.Thumbnails
import net.sourceforge.tess4j.util.ImageHelper
import java.awt.BorderLayout
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImageOp
import java.awt.image.ConvolveOp
import java.awt.image.Kernel
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel


fun BufferedImage.show(): JFrame {
    val frame = JFrame().apply {
        this.title = "stained_image"
        this.setSize(this.width / 2, this.height / 2)
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
    return frame
}

fun BufferedImage.save(file: File) {
    ImageIO.write(this, "png", file)
}

fun BufferedImage.getHSV(x: Int, y: Int): Pixel {
    //Get RGB Value
    val rgb: Int = this.getRGB(x, y)
    //Convert to three separate channels
    val r = 0x00ff0000 and rgb shr 16
    val g = 0x0000ff00 and rgb shr 8
    val b = 0x000000ff and rgb
    val hsv = FloatArray(3)
    Color.RGBtoHSB(r, g, b, hsv)

    return Pixel(r, g, b, hsv[0] * 360, hsv[1], hsv[2])
}

fun BufferedImage.gray(): BufferedImage {
    val image = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
    val g = image.graphics
    g.drawImage(this, 0, 0, null)
    g.dispose()
    return image
}

fun BufferedImage.blackWhite(): BufferedImage {
    val bw = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    for (y in 0 until height) {
        for (x in 0 until width) {

            //Get RGB Value
            val `val`: Int = this.getRGB(x, y)
            //Convert to three separate channels
            val r = 0x00ff0000 and `val` shr 16
            val g = 0x0000ff00 and `val` shr 8
            val b = 0x000000ff and `val`

            val value = r + g + b

            if (value > 230 * 3) {
                bw.setRGB(x, y, Color.WHITE.rgb)
            } else {
                bw.setRGB(x, y, Color.BLACK.rgb)
            }
        }
    }
    return bw
}

fun BufferedImage.startEndX(x0: Int, x1: Int, y: Int, check: (pixel: Pixel) -> Boolean): Pair<Int, Int> {
    var m = this.width
    var M = -1
    for (x in x0 until x1) {
        val pixel = this.getHSV(x, y)
        if (check(pixel)) {
            if (m > x) m = x
            if (M < x) M = x
        }
    }
    return Pair(m, M)
}

fun BufferedImage.blur(): BufferedImage {
    val matrix = FloatArray(9)
    for (i in matrix.indices) {
        matrix[i] = 0.111f
    }

    val op: BufferedImageOp = ConvolveOp(Kernel(3, 3, matrix))
    val bufferedImage = BufferedImage(width, height, this.type)
    op.filter(this, bufferedImage)
    return bufferedImage
}

fun BufferedImage.resize(width: Int, height: Int): BufferedImage {
    return Thumbnails.of(this).forceSize(width, height).asBufferedImage()
}

fun BufferedImage.drawLine(x0: Int, x1: Int, y: Int, color: Color) {
    for (x in x0 until x1) {
        this.setRGB(x, y, color.rgb)
    }
}

fun BufferedImage.drawLongLine(y: Int, color: Color) {
    for (x in 0 until this.width) {
        this.setRGB(x, y-1, color.rgb)
        this.setRGB(x, y, color.rgb)
        this.setRGB(x, y+1, color.rgb)
    }
}

fun BufferedImage.deskew(): BufferedImage {
    val imgdeskew = ImageDeskew(this) // BufferedImage img
    return ImageHelper.rotateImage(this, -imgdeskew.skewAngle) // rotateImage static method
}
