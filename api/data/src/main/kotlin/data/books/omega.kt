package data.books

import data.app.*
import data.extend.*
import java.awt.Color
import java.io.File
import kotlin.math.abs

data class Omega(val resourceZipPath: String, val startingTeorijaName: String) {

    val resourceFile = File("src/main/resources")
    val zipFile = File(resourceFile, resourceZipPath)
    val saveDir = File(resourceFile, resourceZipPath.split('.').first())
    var teorijaDir = File(saveDir, startingTeorijaName)
    val resitveDir = File(saveDir, "resitve")

    init {
        init_tessaract()
    }

    fun omega_zip_iterator(file: File, start: Int, end: Int, debug: Boolean) = sequence {
        for ((zipImageNum, zipImage) in zip_iterator(skip = start, end = end, file = file).withIndex()) {

            val vrstice = mutableMapOf<ZipPart.Tip, Int>() // Stetje zaznanih aktivnih vrstic
            val image = zipImage.deskew() //Fix image rotation
            val bluredImage = image.blur(repeat = 5) // Stabilize pixels
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
                    if (debug) bluredImage.drawLine(minRed, maxRed, y, Color.CYAN)
                    vrstice.increment(ZipPart.Tip.naloga)
                    vrstice.reset(ZipPart.Tip.prazno)
                    continue
                }

                //Detekcija naslova
                if (maxRed - minRed > image.width * 8 / 10 && minRed < 300 && image.width - 300 < maxRed) {
                    if (debug) bluredImage.drawLine(minRed, maxRed, y, Color.MAGENTA)
                    vrstice.increment(ZipPart.Tip.naslov)
                    vrstice.reset(ZipPart.Tip.prazno)
                    continue
                }

                //Detekcija teorije
                if (maxRedFaint - minRedFaint > image.width * 8 / 10 && minRedFaint < 300 && image.width - 300 < maxRedFaint) {
                    if (debug) bluredImage.drawLine(minRedFaint, maxRedFaint, y, Color.ORANGE)
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
                    if (max.key == ZipPart.Tip.prazno) continue

                    if (debug) bluredImage.drawLongLine(y - stevilo_praznih_vrstic, Color.BLACK)
                    if (debug) bluredImage.drawLongLine(y - stevilo_praznih_vrstic - max.value, Color.BLACK)


                    omegaSlika.parts.add(
                        ZipPart(
                            tip = max.key,
                            yStart = y - stevilo_praznih_vrstic - max.value - 30,
                            yEnd = y - stevilo_praznih_vrstic + 30
                        )
                    )

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
                    current.yEnd = next.yStart + 25
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
                        if (debug) bluredImage.drawLongLine(y, Color.RED)

                        //Ce se nisi skocil preko teksta
                        if (jumped_over_text == 0) {
                            jumped_over_text++ //Povecaj stevec skokov
                            y -= 50 //Dejansko skoci preko tekst
                            continue //Isci se naprej
                        }

                        opLast.yEnd =
                            y + 30 //Na koncu nastavi konec zadnje naloge tam kjer si nameraval skociti drugic.
                        break
                    }
                }
            }

            if (debug) {
                val debugImage = bluredImage.resize(image.width / 3, image.height / 3)
                debugImage.show()
                debugImage.save(File("${zipImageNum}_debug.png"))
                image.save(File("${zipImageNum}_original.png"))
                System.exit(0)
            }

            /**
             * Popravki partsov in ustvarjanje njihovih slik!
             */
            for (op in omegaSlika.parts) {
                op.image = image.getSafeSubimage(0, op.yStart, image.width, abs(op.yEnd - op.yStart))
            }

            yield(omegaSlika)
        }
    }

    fun naloge(start: Int, end: Int, debug: Boolean) {
        saveDir.mkdir()

        var currentNaloga = 0
        val iterator = omega_zip_iterator(start = start, end = end, file = this.zipFile, debug = debug)
        for ((index, slika) in iterator.withIndex()) {
            for (part in slika.parts) {

                val bb = part.image.boundBox(70, 80)

                when (part.tip) {
                    ZipPart.Tip.naloga -> {
                        part.image =
                            part.image.getSafeSubimage(bb.x0 - 30, bb.y0 - 40, bb.width() + 100, bb.height() + 80)

                        val text = google_ocr(part.image)
                        val stevilkaNaloge = try {
                            text.split(" ").first().replace(".", "").replace("*", "").toInt()
                        } catch (err: NumberFormatException) {
                            ++currentNaloga
                        }
                        currentNaloga = stevilkaNaloge
                        val dir = File(teorijaDir, "$stevilkaNaloge")
                        val imgFile = File(dir, "vsebina.png")
                        val txtFile = File(dir, "vsebina.txt")

                        dir.mkdir()

                        part.image.save(imgFile)
                        txtFile.writeText(text)
                    }

                    ZipPart.Tip.naslov -> {
                        part.image = part.image.getSafeSubimage(bb.x0, bb.y0, bb.width(), bb.height())

                        val ocrImage = part.image.blackWhite().negative()
                        val text = google_ocr(ocrImage)

                        teorijaDir = File(saveDir, text)
                        teorijaDir.mkdir()
                    }

                    ZipPart.Tip.teorija -> {
                        val dir = File(teorijaDir, "teorija")
                        val teorijaFile = File(dir, "vsebina.png")
                        val vsebinaFile = File(dir, "vsebina.txt")
                        val text = google_ocr(part.image)

                        dir.mkdir()
                        vsebinaFile.writeText(text)

                        part.image = part.image.getSafeSubimage(bb.x0, bb.y0, bb.width(), bb.height())
                        part.image.save(teorijaFile)
                    }

                    ZipPart.Tip.prazno -> {
                        println("PRAZNO: $index")
                        part.image.show()
                    }
                }
            }
        }
    }

    fun resitve(start: Int, end: Int, debug: Boolean) {
        resitveDir.mkdir()

        var currentNaloga = 0
        val resitve_iterator = omega_zip_iterator(start = start, end = end, file = this.zipFile, debug = debug)

        for ((index, slika) in resitve_iterator.withIndex()) {
            for (part in slika.parts) {

                val bb = part.image.boundBox(70, 80)

                part.image =
                    part.image.getSafeSubimage(bb.x0 - 30, bb.y0 - 40, bb.width() + 100, bb.height() + 80)
                val text = google_ocr(part.image)
                val stevilkaNaloge = try {
                    text.split(" ").first().replace(".", "").replace("*", "").toInt()
                } catch (err: NumberFormatException) {
                    ++currentNaloga
                }
                currentNaloga = stevilkaNaloge
                val dir = File(resitveDir, "$stevilkaNaloge")
                val imgFile = File(dir, "resitev.png")
                val txtFile = File(dir, "resitev.txt")

                dir.mkdir()
                part.image.save(imgFile)
                txtFile.writeText(text)

                if (part.tip != ZipPart.Tip.naloga) {
                    println("PRAZNO: $index")
                    part.image.show()
                }
            }
        }
    }

}
