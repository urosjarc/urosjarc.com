package data

import net.sourceforge.tess4j.Tesseract
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.imageio.ImageIO
import kotlin.math.abs

val tesseract = Tesseract()

fun init_tessaract() {
    tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");

    tesseract.setLanguage("slv")
    tesseract.setPageSegMode(11)
    tesseract.setOcrEngineMode(1)

    tesseract.setVariable("tessedit_create_hocr", "1")
    tesseract.setVariable("user_defined_dpi", "300");
}

fun zip_iterator(file: File, skip: Int): Sequence<BufferedImage> {
    val zipFile = ZipFile(file.absolutePath)
    val entries = zipFile.entries()

    return sequence {
        var i = 0
        while (entries.hasMoreElements()) {
            val zipEntry: ZipEntry = entries.nextElement()

            if (++i < skip) continue

            val inputStream = zipFile.getInputStream(zipEntry)


            val bufferedImage = ImageIO.read(inputStream)
            yield(bufferedImage)
        }
        zipFile.close()
    }
}

data class OmegaPart(
    val tip: Tip,
    var yStart: Int,
    var yEnd: Int = -1,
    var image: BufferedImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
) {
    enum class Tip { naloga, naslov, teorija, prazno }
}

fun omega_zip_iterator(skip: Int) = sequence<List<OmegaPart>> {
    for (zipImage in zip_iterator(
        skip = skip,
        file = File("/home/urosjarc/vcs/urosjarc.com2/api/data/src/main/resources/Omega11.zip")
    )) {

        val omegaParts = mutableListOf<OmegaPart>()
        val image = zipImage.deskew()
        val bluredImage = image.blur().blur().blur().blur().blur()

        var vrstice = mutableMapOf(
            OmegaPart.Tip.prazno to 0,
            OmegaPart.Tip.naslov to 0,
            OmegaPart.Tip.teorija to 0,
            OmegaPart.Tip.naloga to 0
        )

        for (y in 50 until image.height - 50) {

            var minRed = image.width
            var maxRed = 0
            var minRedFaint = image.width
            var maxRedFaint = 0

            //Detekcija rdece barve
            for (x in 50 until image.width - 100) {
                val pixel = bluredImage.getHSV(x, y)
                if (pixel.is_red()) {
                    if (minRed > x) minRed = x
                    if (maxRed < x) maxRed = x
                } else if (pixel.is_red_faint()) {
                    if (minRedFaint > x) minRedFaint = x
                    if (maxRedFaint < x) maxRedFaint = x
                }
            }

            //Detekcija naloge
            if (maxRed - minRed < 200 && maxRed < 300 && minRed < 300) {
                image.drawLine(minRed, maxRed, y, Color.CYAN)
                vrstice.merge(OmegaPart.Tip.naloga, 1, Int::plus)
                vrstice.set(OmegaPart.Tip.prazno, 0)
                continue
            }

            //Detekcija naslova
            if (maxRed - minRed > image.width * 8 / 10 && minRed < 300 && image.width - 300 < maxRed) {
                image.drawLine(minRed, maxRed, y, Color.MAGENTA)
                vrstice.merge(OmegaPart.Tip.naslov, 1, Int::plus)
                vrstice.set(OmegaPart.Tip.prazno, 0)
                continue
            }

            //Detekcija teorije
            if (maxRedFaint - minRedFaint > image.width * 8 / 10 && minRedFaint < 300 && image.width - 300 < maxRedFaint) {
                image.drawLine(minRedFaint, maxRedFaint, y, Color.ORANGE)
                vrstice.merge(OmegaPart.Tip.teorija, 1, Int::plus)
                vrstice.set(OmegaPart.Tip.prazno, 0)
                continue
            }

            //Detekcija prazne vrstice
            vrstice.merge(OmegaPart.Tip.prazno, 1, Int::plus)
            val stevilo_praznih_vrstic = vrstice.getOrDefault(OmegaPart.Tip.prazno, 0)
            if (stevilo_praznih_vrstic > 20) {
                val max = vrstice.maxBy { it.value }

                when (max.key) {
                    OmegaPart.Tip.prazno -> continue
                    else -> {
                        omegaParts.add(
                            OmegaPart(
                                tip = max.key,
                                yStart = y - stevilo_praznih_vrstic - max.value,
                                yEnd = y - stevilo_praznih_vrstic
                            )
                        )
                    }
                }

                for ((key: OmegaPart.Tip, value: Int) in vrstice) {
                    vrstice[key] = 0
                }
            }
        }

        for (i in 0 until omegaParts.size - 1) {
            val current = omegaParts[i]
            val next = omegaParts[i + 1]
            if (current.tip == OmegaPart.Tip.naloga) {
                current.yEnd = next.yStart
            }
        }


        var jumped_over_footer = false
        var y = image.height - 50
        while (--y > 0) {
            var black_pikslov = 0

            //Detekcija rdece barve
            for (x in 50 until image.width - 100) {
                val pixel = bluredImage.getHSV(x, y)
                if (!pixel.is_white()) {
                    black_pikslov++
                }
            }

            if (black_pikslov > 10) {
                if (jumped_over_footer) {
                    image.drawLine(0, image.width, y, Color.BLACK)
                    val lastOmegaPart = omegaParts.last()
                    if (lastOmegaPart.tip == OmegaPart.Tip.naloga) lastOmegaPart.yEnd = y
                    break
                } else {
                    jumped_over_footer = true
                    image.drawLine(0, image.width, y, Color.RED)
                    y -= 50
                }
            }
        }

        //Correcting image box
        for (op in omegaParts) {
            op.yStart -= 10
            op.image = image.getSubimage(0, op.yStart, image.width, abs(op.yEnd - op.yStart))
        }

        yield(omegaParts)
    }
}

fun main() {
    for (parts in omega_zip_iterator(skip=7)) {
        for (part in parts) {
            part.image.resize(part.image.width / 3, part.image.height / 3).show()
            Thread.sleep(1000)
        }
    }
}
