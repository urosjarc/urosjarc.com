package data.books

import data.app.ZipPart
import data.app.ZipSlika
import data.app.zip_iterator
import data.extend.*
import java.awt.Color
import java.io.File
import kotlin.math.abs

fun omega_zip_iterator(file: File, skip: Int) = sequence {
    for (zipImage in zip_iterator(skip = skip, file = file)) {

        val vrstice = mutableMapOf<ZipPart.Tip, Int>() // Stetje zaznanih aktivnih vrstic
        val image = zipImage.deskew() //Fix image rotation
        val bluredImage = image.blur().blur().blur().blur().blur() // Stabilize pixels
        val omegaSlika = ZipSlika(image = image)

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
                vrstice.increment(ZipPart.Tip.naloga)
                vrstice.reset(ZipPart.Tip.prazno)
                continue
            }

            //Detekcija naslova
            if (maxRed - minRed > image.width * 8 / 10 && minRed < 300 && image.width - 300 < maxRed) {
                image.drawLine(minRed, maxRed, y, Color.MAGENTA)
                vrstice.increment(ZipPart.Tip.naslov)
                vrstice.reset(ZipPart.Tip.prazno)
                continue
            }

            //Detekcija teorije
            if (maxRedFaint - minRedFaint > image.width * 8 / 10 && minRedFaint < 300 && image.width - 300 < maxRedFaint) {
                image.drawLine(minRedFaint, maxRedFaint, y, Color.ORANGE)
                vrstice.increment(ZipPart.Tip.teorija)
                vrstice.reset(ZipPart.Tip.prazno)
                continue
            }

            //Detekcija prazne vrstice
            vrstice.increment(ZipPart.Tip.prazno)

            //Detekcija ali je praznih vrstic dovolj za analizo prestetih vrstic
            val stevilo_praznih_vrstic = vrstice.getOrDefault(ZipPart.Tip.prazno, 0)
            if (stevilo_praznih_vrstic > 20) {

                //Dobi tip z najvecjim stevilom vrstic
                val max = vrstice.maxBy { it.value }

                //Ce je najvecje stevilo vrstic nekih elementov potem ustvari del z pripadajocim tipom.
                when (max.key) {
                    ZipPart.Tip.prazno -> continue
                    else -> {
                        image.drawLongLine(y - stevilo_praznih_vrstic, Color.BLACK)
                        image.drawLongLine(y - stevilo_praznih_vrstic - max.value, Color.BLACK)
                        omegaSlika.parts.add(
                            ZipPart(
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
            if (current.tip == ZipPart.Tip.naloga) {
                current.yEnd = next.yStart
            }
        }

        /**
         * Iskanje kje se zgodi konec samo ce je na koncu naloga
         */
        val opLast = omegaSlika.parts.lastOrNull()
        if (opLast != null && opLast.tip == ZipPart.Tip.naloga) {
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
