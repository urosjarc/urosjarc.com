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

fun BufferedImage.izrezi(okvir: Okvir): BufferedImage {
    try {
        return this.getSubimage(okvir.start.x, okvir.start.y, okvir.sirina, okvir.visina)
    } catch (err: Throwable) {
        println("$okvir, $this")
        this.prikazi()
        return this
    }
}

fun BufferedImage.kopiraj(): BufferedImage {
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
            val pixel = this.piksel(x, y)
            if (!pixel.is_white()) pixels.add(pixel)
        }
    }
    return Piksel.average(pixels)
}

fun BufferedImage.steviloPikslov(okvir: Okvir, countOn: (Piksel) -> Boolean): Int {
    var count = 0
    for (y in okvir.start.y..okvir.end.y) {
        for (x in okvir.start.x..okvir.end.x) {
            val pixel = this.piksel(x, y)
            if (countOn(pixel)) ++count
        }
    }
    return count
}

fun BufferedImage.odstrani_prazen_prostor(margin: Int = 10): BufferedImage {
    return this
//    var img = this.kopiraj()
//    img = img.izrezi(okvir = img.boundBox(margin = 0, countOn = { pix -> pix.is_black() || pix.is_red() }, stopOn = { it == 0 }))
//    return img.izrezi(okvir = img.boundBox(margin = margin, countOn = { pix -> pix.is_black() || pix.is_red() }, stopOn = { it > 5 }))
}

fun BufferedImage.piksel(x: Int, y: Int): Piksel {
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
    var widthCounter = 0
    val bin = this.binarna()
    for (i in 1..maxWidth) {
        val xStart = i
        val yStart = i
        val xEnd = this.width - i
        val yEnd = this.height - i
        var counter = 0

        //Up down
        for (y in yStart until yEnd) {
            val left = bin.piksel(x = xStart, y = y)
            val right = bin.piksel(x = xEnd, y = y)
            if (left.is_black() || right.is_black()) counter++
        }

        //Left right
        for (x in xStart until xEnd) {
            val up = bin.piksel(y = yStart, x = x)
            val down = bin.piksel(y = yEnd, x = x)
            if (up.is_black() || down.is_black()) counter++
        }

        if (counter == 0) widthCounter++
        else widthCounter = 0

        if (widthCounter >= 3) return Pair(i, this.getSubimage(xStart, yStart, xEnd - xStart, yEnd - yStart))
    }
    return Pair(0, this.getSubimage(0, 0, this.width, this.height))
}

fun BufferedImage.rotiraj(angle: Double): BufferedImage {
    return ImageHelper.rotateImage(this, angle)
}

fun BufferedImage.boundBox(margin: Int = 0, countOn: (Piksel) -> Boolean, stopOn: (Int) -> Boolean): Okvir {
    var start_y = 0
    var start_x = 0
    var end_y = this.height
    var end_x = this.width

    //up to down
    for (y in 0 until this.height) {
        var count = 0
        for (x in 0 until this.width) if (countOn(this.piksel(x, y))) count++
        if (stopOn(count)) {
            start_y = y; break
        }
    }

    //down to up
    for (y in this.height - 1 downTo 0) {
        var count = 0
        for (x in 0 until this.width) if (countOn(this.piksel(x, y))) count++
        if (stopOn(count)) {
            end_y = y; break
        }
    }

    //left to right
    for (x in 0 until this.width) {
        var count = 0
        for (y in 0 until this.height) if (countOn(this.piksel(x, y))) count++
        if (stopOn(count)) {
            start_x = x; break
        }
    }

    //right to left
    for (x in this.width - 1 downTo 0) {
        var count = 0
        for (y in 0 until this.height) if (countOn(this.piksel(x, y))) count++
        if (stopOn(count)) {
            end_x = x; break
        }
    }

    return Okvir(
        start = Vektor(
            x = maxOf(0, start_x - margin),
            y = maxOf(0, start_y - margin)
        ),
        end = Vektor(
            x = minOf(this.width, end_x + margin),
            y = minOf(this.height, end_y + margin)
        )
    )
}

val BufferedImage.okvir: Okvir get() = Okvir(start = Vektor(x = 0, y = 0), end = Vektor(x = this.width, y = this.height))

fun BufferedImage.narisiMrezo(dx: Int = 100, dy: Int = 100, w: Int = 2, color: Color = Color.DARK_GRAY): BufferedImage {
    val new = this.kopiraj()
    for (x in dx..new.width - dx / 2 step dx) {
        for (y in dy / 2..new.height - dy / 2) {
            for (i in 0..w) {
                val col = if (x <= 2 * dx) Color.MAGENTA else color
                new.setRGB(x + i, y, col.rgb)
            }
        }
    }
    for (y in dy..new.height - dy / 2 step dy) {
        for (x in dx / 2..new.width - dx / 2) {
            for (i in 0..w) {
                val col = if (y <= 2 * dy) Color.MAGENTA else color
                new.setRGB(x, y + i, col.rgb)
            }
        }
    }
    return new
}

fun BufferedImage.binarna(negativ: Boolean = false): BufferedImage {
    val bw = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    for (y in 0 until height) {
        for (x in 0 until width) {
            val p = this.piksel(x, y)
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

val BufferedImage.inputStream: ByteArrayInputStream
    get() {
        val os = ByteArrayOutputStream()
        ImageIO.write(this, "png", os)
        return ByteArrayInputStream(os.toByteArray())
    }
