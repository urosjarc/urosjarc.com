package gui.extend

import com.recognition.software.jdeskew.ImageDeskew
import gui.domain.Okvir
import gui.domain.Piksel
import gui.domain.Vektor
import net.coobird.thumbnailator.Thumbnails
import net.sourceforge.tess4j.util.ImageHelper
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.image.ConvolveOp
import java.awt.image.Kernel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel


fun BufferedImage.prikazi(title: String = ""): JFrame {
    val frame = JFrame().apply {
        this.title = title
        this.setSize(this.width / 2, this.height / 2)
        this.defaultCloseOperation = javax.swing.WindowConstants.EXIT_ON_CLOSE
    }
    val label = JLabel().apply {
        this.icon = ImageIcon(this@prikazi)
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

fun BufferedImage.spremeniVelikost(w: Int, h: Int): BufferedImage = Thumbnails.of(this)
    .size(w, h).keepAspectRatio(false).asBufferedImage()

fun BufferedImage.shrani(file: File) {
    ImageIO.write(this, "png", file)
}

fun BufferedImage.copiraj(): BufferedImage {
    val b = BufferedImage(this.getWidth(), this.getHeight(), this.getType())
    val g: Graphics = b.graphics
    g.drawImage(this, 0, 0, null)
    g.dispose()
    return b
}

fun BufferedImage.dodajSpodaj(img: BufferedImage): BufferedImage {
    val w: Int = Math.max(this.width, img.width)
    val h: Int = this.height + img.height
    val combined = BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
    val g = combined.createGraphics()
    g.drawImage(this, 0, 0, null)
    g.drawImage(img, 0, this.height, null)
    g.dispose()
    return combined
}

fun BufferedImage.povprecenPiksel(okvir: Okvir): Piksel {
    val pixels = mutableListOf<Piksel>()

    for (y in okvir.start.y..okvir.end.y) {
        for (x in okvir.start.x..okvir.end.x) {
            val pixel = this.HSV(x, y)
            if (!pixel.is_white()) pixels.add(pixel)
        }
    }
    return Piksel.average(pixels)
}

fun BufferedImage.HSV(x: Int, y: Int): Piksel {
    //Get RGB Value
    val rgb: Int = this.getRGB(x, y)
    //Convert to three separate channels
    val r = 0x00ff0000 and rgb shr 16
    val g = 0x0000ff00 and rgb shr 8
    val b = 0x000000ff and rgb
    val hsv = FloatArray(3)
    Color.RGBtoHSB(r, g, b, hsv)

    return Piksel(r, g, b, hsv[0] * 360, hsv[1], hsv[2])
}

fun BufferedImage.poravnaj(): Pair<Double, BufferedImage> {
    val imgdeskew = ImageDeskew(this) // BufferedImage img
    return Pair(-imgdeskew.skewAngle, this.rotiraj(-imgdeskew.skewAngle)) // rotateImage static method
}

fun BufferedImage.zamegliSliko(radij: Int): BufferedImage {
    val size = radij * 2 + 1
    val weight = 1.0f / (size * size)
    val data = FloatArray(size * size)
    data.fill(weight)
    val kernel = Kernel(size, size, data)
    val op = ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null)
    return op.filter(this, null)
}

fun BufferedImage.odstraniObrobo(maxWidth: Int): Pair<Int, BufferedImage> {
    for (i in 1..maxWidth) {
        val xStart = i
        val yStart = i
        val xEnd = this.width - 2 * i
        val yEnd = this.height - 2 * i
        var counter = 0

        //Up down
        for (y in yStart until yEnd) {
            val left = this.HSV(x = xStart, y = y)
            val right = this.HSV(x = xEnd, y = y)
            if (!left.is_black() || !right.is_black()) {
                counter++
            }
        }

        //Left right
        for (x in xStart until xEnd) {
            val up = this.HSV(y = yStart, x = x)
            val down = this.HSV(y = yEnd, x = x)
            if (!up.is_black() || !down.is_black()) {
                counter++
            }
        }

        if (counter == 0) {
            return Pair(i, this.getSubimage(xStart, yStart, xEnd - xStart, yEnd - yStart))
        }
    }
    return Pair(0, this.getSubimage(0, 0, this.width, this.height))
}

fun BufferedImage.rotiraj(angle: Double): BufferedImage {
    return ImageHelper.rotateImage(this, angle)
}

fun BufferedImage.boundBox(): Okvir {
    val maxCount = 10
    var start_y = 0
    var start_x = 0
    var end_y = 0
    var end_x = 0

    //up to down
    var count = 0
    start@ for (y in 0 until this.height) {
        for (x in 0 until this.width) {
            if (!this.HSV(x, y).is_white()) count++
            if (count > maxCount) {
                start_y = y
                break@start
            }
        }
    }

    //down to up
    count = 0
    start@ for (y in this.height - 1 downTo 0) {
        for (x in 0 until this.width) {
            if (!this.HSV(x, y).is_white()) count++
            if (count > maxCount) {
                end_y = y
                break@start
            }
        }
    }

    //left to right
    count = 0
    start@ for (x in 0 until this.width) {
        for (y in 0 until this.height) {
            if (!this.HSV(x, y).is_white()) count++
            if (count > maxCount) {
                start_x = x
                break@start
            }
        }
    }

    //right to left
    count = 0
    start@ for (x in this.width - 1 downTo 0) {
        for (y in 0 until this.height) {
            if (!this.HSV(x, y).is_white()) count++
            if (count > maxCount) {
                end_x = x
                break@start
            }
        }
    }

    return Okvir(start = Vektor(x = start_x, y = start_y), end = Vektor(x = end_x, y = end_y))
}

val BufferedImage.okvir: Okvir get() = Okvir(start= Vektor(x=0, y=0), end = Vektor(x=this.width, y=this.height))

fun BufferedImage.narisiMrezo(dx: Int = 100, dy: Int = 100, w: Int = 2, color: Color = Color.DARK_GRAY) {
    for (x in dx..this.width - dx / 2 step dx) {
        for (y in dy / 2..this.height - dy / 2) {
            for (i in 0..w) {
                this.setRGB(x + i, y, color.rgb)
            }
        }
    }
    for (y in dy..this.height - dy / 2 step dy) {
        for (x in dx / 2..this.width - dx / 2) {
            for (i in 0..w) {
                this.setRGB(x, y + i, color.rgb)
            }
        }
    }
}

fun BufferedImage.binarna(negativ: Boolean = false): BufferedImage {
    val bw = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    for (y in 0 until height) {
        for (x in 0 until width) {
            val p = this.HSV(x, y)
            val value = p.r + p.g + p.b

            if (value > 230 * 3) {
                bw.setRGB(x, y, if (negativ) Color.BLACK.rgb else Color.WHITE.rgb)
            } else {
                bw.setRGB(x, y, if (negativ) Color.WHITE.rgb else Color.BLACK.rgb)
            }
        }
    }
    return bw
}

val BufferedImage.inputStream: ByteArrayInputStream get() {
    val os = ByteArrayOutputStream()
    ImageIO.write(this, "png", os)
    return ByteArrayInputStream(os.toByteArray())
}
