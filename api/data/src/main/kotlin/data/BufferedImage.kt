package data

import com.google.cloud.vision.v1.BoundingPoly
import com.recognition.software.jdeskew.ImageDeskew
import net.coobird.thumbnailator.Thumbnails
import net.sourceforge.tess4j.util.ImageHelper
import java.awt.BorderLayout
import java.awt.Color
import java.awt.image.BufferedImage
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

fun BufferedImage.checkBoundBox(boundingPoly: BoundingPoly, check: (pixel: Pixel) -> Boolean): Boolean {

    val (xMin, xMax) = boundingPoly.xMinMax()
    val (yMin, yMax) = boundingPoly.yMinMax()

    for (y in yMin..yMax) {
        for (x in xMin..xMax) {
            val pixel = this.getHSV(x, y)
            if (!check(pixel)) {
                println(pixel)
                return false
            }
        }
    }
    return true
}

fun BufferedImage.drawBoundBox(boundingPoly: BoundingPoly, color: Color) {

    val (xMin, xMax) = boundingPoly.xMinMax()
    val (yMin, yMax) = boundingPoly.yMinMax()

    for (y in yMin..yMax) {
        for (x in xMin..xMax) {

            this.setRGB(x, y, color.rgb)
        }
    }
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

fun BufferedImage.getSafeSubimage(x: Int, y: Int, w: Int, h: Int): BufferedImage {
    val x0 = if (x < 0) 0 else x
    val y0 = if (y < 0) 0 else y
    var x1 = x0 + w
    var y1 = y0 + h

    if (x1 > this.width - 1) x1 = this.width - 1
    if (y1 > this.height - 1) y1 = this.height - 1

    return this.getSubimage(x0, y0, x1 - x0, y1 - y0)
}

fun BufferedImage.resize(width: Int, height: Int): BufferedImage {
    return Thumbnails.of(this).forceSize(width, height).asBufferedImage()
}

fun BufferedImage.deskew(): BufferedImage {
    val imgdeskew = ImageDeskew(this) // BufferedImage img
    return ImageHelper.rotateImage(this, -imgdeskew.skewAngle) // rotateImage static method
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


fun BufferedImage.boundBox(xStart: Int, xEnd: Int): BoundBox {
    val maxCount = 5
    val boundBox = BoundBox(-1, -1, -1, -1)

    //up to down
    var count = 0
    start@ for (y in 0 until this.height) {
        for (x in xStart until this.width - xEnd) {
            if (!this.getHSV(x, y).is_white()) count++
            if (count > maxCount) {
                boundBox.y0 = y
                break@start
            }
        }
    }

    //down to up
    count = 0
    start@ for (y in this.height - 1 downTo 0) {
        for (x in xStart until this.width - xEnd) {
            if (!this.getHSV(x, y).is_white()) count++
            if (count > maxCount) {
                boundBox.y1 = y
                break@start
            }
        }
    }

    //left to right
    count = 0
    start@ for (x in xStart until this.width - xEnd) {
        for (y in 0 until this.height) {
            if (!this.getHSV(x, y).is_white()) count++
            if (count > maxCount) {
                boundBox.x0 = x
                break@start
            }
        }
    }

    //right to left
    count = 0
    start@ for (x in this.width - 1 - xEnd downTo xStart) {
        for (y in 0 until this.height) {
            if (!this.getHSV(x, y).is_white()) count++
            if (count > maxCount) {
                boundBox.x1 = x
                break@start
            }
        }
    }

    return boundBox
}
