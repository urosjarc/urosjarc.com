package data.extend

import com.recognition.software.jdeskew.ImageDeskew
import data.domain.Anotacija
import data.domain.AnotacijeStrani
import data.domain.Okvir
import data.domain.Piksel
import net.coobird.thumbnailator.Thumbnails
import net.sourceforge.tess4j.util.ImageHelper
import java.awt.BasicStroke
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel


fun BufferedImage.show(title: String = ""): JFrame {
    val frame = JFrame().apply {
        this.title = title
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

fun BufferedImage.drawAnnotations(annotations: Collection<Anotacija>, color: Color = Color.CYAN, width: Float = 5.0f) {
    val g = this.createGraphics()
    g.color = color
    g.stroke = BasicStroke(width)
    for (a in annotations) {
        g.drawRect(a.x, a.y, a.width, a.height)
    }
    g.dispose()
}
fun BufferedImage.copy(): BufferedImage {
    val b = BufferedImage(this.getWidth(), this.getHeight(), this.getType())
    val g: Graphics = b.graphics
    g.drawImage(this, 0, 0, null)
    g.dispose()
    return b
}

fun BufferedImage.drawSlikaAnnotations(slika: AnotacijeStrani) {
    this.drawAnnotations(slika.noga, Color.BLACK)
    slika.naloge.forEach { this.drawAnnotations(it, Color.GREEN) }
    this.drawAnnotations(slika.naslov, Color.BLUE)
    this.drawAnnotations(slika.glava, Color.BLACK)
    this.drawAnnotations(slika.teorija, Color.RED)
}

fun BufferedImage.append(img: BufferedImage): BufferedImage {
    val w: Int = Math.max(this.getWidth(), img.getWidth())
    val h: Int = this.getHeight() + img.getHeight()
    val combined = BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
    val g = combined.graphics
    g.drawImage(this, 0, 0, null)
    g.drawImage(img, 0, h, null)
    g.dispose()
    return combined
}

fun BufferedImage.averagePixel(a: Anotacija): Piksel {
    val pixels = mutableListOf<Piksel>()

    for (y in a.y..a.y_max) {
        for (x in a.x..a.x_max) {
            val pixel = this.getHSV(x, y)
            if (!pixel.is_white()) pixels.add(pixel)
        }
    }
    return Piksel.average(pixels)
}

fun BufferedImage.getHSV(x: Int, y: Int): Piksel {
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

fun BufferedImage.deskew(): Pair<Double, BufferedImage> {
    val imgdeskew = ImageDeskew(this) // BufferedImage img
    return Pair(-imgdeskew.skewAngle, this.rotate(-imgdeskew.skewAngle)) // rotateImage static method
}

fun BufferedImage.removeBorder(maxWidth: Int): Pair<Int, BufferedImage> {
    for (i in 1..maxWidth) {
        val xStart = i
        val yStart = i
        val xEnd = this.width - 2 * i
        val yEnd = this.height - 2 * i
        var counter = 0

        //Up down
        for (y in yStart until yEnd) {
            val left = this.getHSV(x = xStart, y = y)
            val right = this.getHSV(x = xEnd, y = y)
            if (!left.is_black() || !right.is_black()) {
                counter++
            }
        }

        //Left right
        for (x in xStart until xEnd) {
            val up = this.getHSV(y = yStart, x = x)
            val down = this.getHSV(y = yEnd, x = x)
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

fun BufferedImage.rotate(angle: Double): BufferedImage {
    return ImageHelper.rotateImage(this, angle)
}

fun BufferedImage.boundBox(xStart: Int, xEnd: Int): Okvir {
    val maxCount = 5
    val boundBox = Okvir(-1, -1, -1, -1)

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

fun BufferedImage.gray(): BufferedImage {
    val image = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
    val g = image.graphics
    g.drawImage(this, 0, 0, null)
    g.dispose()
    return image
}

fun BufferedImage.blackWhite(): BufferedImage {
    val bw = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
    for (y in 0 until height) {
        for (x in 0 until width) {
            val p = this.getHSV(x, y)
            val value = p.r + p.g + p.b

            if (value > 255 / 2 * 3) {
                bw.setRGB(x, y, Color.WHITE.rgb)
            } else {
                bw.setRGB(x, y, Color.BLACK.rgb)
            }
        }
    }
    return bw
}

fun BufferedImage.drawGrid(dx: Int = 100, dy: Int = 100, w: Int = 2, color: Color = Color.DARK_GRAY) {
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

fun BufferedImage.negative(): BufferedImage {
    val bw = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    for (y in 0 until height) {
        for (x in 0 until width) {
            val p = this.getHSV(x, y)
            val value = p.r + p.g + p.b

            if (value > 230 * 3) {
                bw.setRGB(x, y, Color.BLACK.rgb)
            } else {
                bw.setRGB(x, y, Color.WHITE.rgb)
            }
        }
    }
    return bw
}

fun BufferedImage.invert(): BufferedImage {
    val bw = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    for (y in 0 until height) {
        for (x in 0 until width) {
            val p = this.getRGB(x, y)
            var col = Color(p, true)
            col = Color(
                255 - col.red,
                255 - col.green,
                255 - col.blue
            )
            bw.setRGB(x, y, col.rgb)
        }
    }
    return bw
}

fun BufferedImage.inputStream(): ByteArrayInputStream {
    val os = ByteArrayOutputStream()
    ImageIO.write(this, "png", os)
    return ByteArrayInputStream(os.toByteArray())
}
