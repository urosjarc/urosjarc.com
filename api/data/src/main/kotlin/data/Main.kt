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

data class OmegaSlika(
    val image: BufferedImage,
    val parts: MutableList<OmegaPart> = mutableListOf()
)

fun omega_zip_iterator(skip: Int) = sequence<OmegaSlika> {
    for (zipImage in zip_iterator(
        skip = skip, file = File("/home/urosjarc/vcs/urosjarc.com2/api/data/src/main/resources/Omega11.zip")
    )) {

        val vrstice = mutableMapOf<OmegaPart.Tip, Int>() // Stetje zaznanih aktivnih vrstic
        val image = zipImage.deskew() //Fix image rotation
        val bluredImage = image.blur().blur().blur().blur().blur() // Stabilize pixels
        val omegaSlika = OmegaSlika(image = image)

        /**
         * Iskanje zgornjih delov
         */
        for (y in 50 until image.height - 50) {

            // Stetje kje se pojavi prvi in zadnji rdec piksel
            val (minRed, maxRed) = bluredImage.startEndX(50, image.width - 100, y) { it.is_red() }
            val (minRedFaint, maxRedFaint) = bluredImage.startEndX(50, image.width - 100, y) { it.is_red_faint() }

            //Detekcija naloge
            if (maxRed - minRed < 200 && maxRed < 300 && minRed < 300) {
                image.drawLine(minRed, maxRed, y, Color.CYAN)
                vrstice.increment(OmegaPart.Tip.naloga)
                vrstice.reset(OmegaPart.Tip.prazno)
                continue
            }

            //Detekcija naslova
            if (maxRed - minRed > image.width * 8 / 10 && minRed < 300 && image.width - 300 < maxRed) {
                image.drawLine(minRed, maxRed, y, Color.MAGENTA)
                vrstice.increment(OmegaPart.Tip.naslov)
                vrstice.reset(OmegaPart.Tip.prazno)
                continue
            }

            //Detekcija teorije
            if (maxRedFaint - minRedFaint > image.width * 8 / 10 && minRedFaint < 300 && image.width - 300 < maxRedFaint) {
                image.drawLine(minRedFaint, maxRedFaint, y, Color.ORANGE)
                vrstice.increment(OmegaPart.Tip.teorija)
                vrstice.reset(OmegaPart.Tip.prazno)
                continue
            }

            //Detekcija prazne vrstice
            vrstice.increment(OmegaPart.Tip.prazno)

            //Detekcija ali je praznih vrstic dovolj za analizo prestetih vrstic
            val stevilo_praznih_vrstic = vrstice.getOrDefault(OmegaPart.Tip.prazno, 0)
            if (stevilo_praznih_vrstic > 20) {

                //Dobi tip z najvecjim stevilom vrstic
                val max = vrstice.maxBy { it.value }

                //Ce je najvecje stevilo vrstic nekih elementov potem ustvari del z pripadajocim tipom.
                when (max.key) {
                    OmegaPart.Tip.prazno -> continue
                    else -> {
                        image.drawLongLine(y - stevilo_praznih_vrstic, Color.BLACK)
                        image.drawLongLine(y - stevilo_praznih_vrstic - max.value, Color.BLACK)
                        omegaSlika.parts.add(
                            OmegaPart(
                                tip = max.key,
                                yStart = y - stevilo_praznih_vrstic - max.value,
                                yEnd = y - stevilo_praznih_vrstic
                            )
                        )
                    }
                }

                //Resetiraj stetje
                vrstice.resetAll()
            }
        }

        /**
         * Nastavi konec delov tam kjer se zacne naslednji del
         */
        for (i in 0 until omegaSlika.parts.size - 1) {
            val current = omegaSlika.parts[i]
            val next = omegaSlika.parts[i + 1]
            if (current.tip == OmegaPart.Tip.naloga) {
                current.yEnd = next.yStart
            }
        }

        /**
         * Iskanje kje se zgodi konec samo ce je na koncu naloga
         */
        val opLast = omegaSlika.parts.lastOrNull()
        if (opLast != null && opLast.tip == OmegaPart.Tip.naloga) {
            var jumped_over_text = 0
            var y = image.height - 80
            while (--y >= 0) {

                //Detekcija ne belih pixlov
                var not_white_pikslov = 0
                for (x in 50 until image.width - 100) {
                    val pixel = bluredImage.getHSV(x, y)
                    if (!pixel.is_white()) {
                        not_white_pikslov++
                    }
                }

                //Ali je ne belih pixlov dovolj?
                if (not_white_pikslov > 5) {
                    image.drawLongLine(y, Color.RED)

                    //Ce se nisi skocil preko teksta
                    if (jumped_over_text == 0) {
                        jumped_over_text++ //Povecaj stevec skokov
                        y -= 50 //Dejansko skoci preko tekst
                        continue //Isci se naprej
                    }

                    opLast.yEnd = y //Na koncu nastavi konec zadnje naloge tam kjer si nameraval skociti drugic.
                    break
                }
            }
        }

        /**
         * Popravki partsov in ustvarjanje njihovih slik!
         */
        for (op in omegaSlika.parts) {
            op.yStart -= 10
            op.image = image.getSubimage(0, op.yStart, image.width, abs(op.yEnd - op.yStart))
        }

        yield(omegaSlika)
    }
}

fun main() {
    var i = 0
    for (slika in omega_zip_iterator(skip = 7)) {
        var jframe = slika.image.resize(slika.image.width / 3, slika.image.height / 3).show()
        Thread.sleep(1000)
        jframe.isVisible = false
         slika.image.save(File("image${i++}.png"))
        for (part in slika.parts) {
            jframe = part.image.resize(part.image.width / 3, part.image.height / 3).show()
            Thread.sleep(1000)
            jframe.isVisible = false
        }

    }
}
