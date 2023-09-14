package data

import com.google.cloud.vision.v1.EntityAnnotation
import java.awt.Color
import java.io.File

fun main() {
    val omega11 = File("src/main/resources/Omega11.zip")
    for (image in zip_iterator(file = omega11, skip = 7, end = 15)) {

        val annotations = google_ocr(image)

        /**
         * Parsanje footerja
         */
        val lowest = annotations.maxBy { it.boundingPoly.yMinMax().second }
        val footer = mutableListOf<EntityAnnotation>()
        val (minY, maxY) = lowest.boundingPoly.yMinMax()
        for (annotation in annotations) {
            val (aveX, aveY) = annotation.boundingPoly.average()
            if (aveY in minY..maxY) {
                image.drawBoundBox(annotation.boundingPoly, Color.BLACK)
                footer.add(annotation)
            }
        }


        /**
         * Parsanje nalog
         */
        val naloge = mutableListOf<EntityAnnotation>()
        for (annotation in annotations) {
            val desc = annotation.description
            val box = annotation.boundingPoly
            if (desc.endsWith(".")) {
                val descNum = desc.replace(".", "").toIntOrNull()
                if (descNum != null && image.checkBoundBox(box) { it.is_color() || it.is_white() }) {
                    image.drawBoundBox(box, Color.CYAN)
                    naloge.add(annotation)
                }
            }
        }


        /**
         * Parsanje naslovov
         */
        val naslovi = mutableListOf<MutableList<EntityAnnotation>>()
        for (annotation in annotations) {
            val desc = annotation.description
            val box = annotation.boundingPoly
            if (desc.contains(".")) {
                val descInfo = desc.split(".")
                val descNum0 = descInfo.first().toIntOrNull()
                val descNum1 = descInfo.last().toIntOrNull()
                if (descNum0 != null && descNum1 != null && image.checkBoundBox(box) { it.is_color() || it.is_white() }) {
                    image.drawBoundBox(box, Color.BLUE)

                    val naslov = mutableListOf<EntityAnnotation>()
                    val (_, xMax) = box.xMinMax()
                    val (yMin, yMax) = box.yMinMax()
                    for (anno in annotations) {
                        val (xAve, yAve) = anno.boundingPoly.average()
                        if (yAve in yMin..yMax && xMax < xAve) {
                            image.drawBoundBox(anno.boundingPoly, Color.BLUE)
                            naslov.add(anno)
                        }
                    }
                    naslovi.add(naslov)
                }
            }
        }

        /**
         * Parsanje headerja
         */
        val highest = (naloge + naslovi.map { it.first() }).minBy { it.boundingPoly.yMinMax().second }
        val header = mutableListOf<EntityAnnotation>()
        val (hminY, hmaxY) = highest.boundingPoly.yMinMax()
        for (annotation in annotations) {
            val (aveX, aveY) = annotation.boundingPoly.average()
            if (aveY < hminY) {
                image.drawBoundBox(annotation.boundingPoly, Color.BLACK)
                header.add(annotation)
            }
        }

        /**
         * Parsanje teorije
         */
        val teorije = mutableListOf<MutableList<EntityAnnotation>>()
        for (naslov in naslovi) {

            val teorija = mutableListOf<EntityAnnotation>()

            /**
             * Iskanje najbljizje naloge
             */
            val (teorija_minY, teorija_maxY) = naslov.first().boundingPoly.yMinMax()
            var closestNaloga: EntityAnnotation? = null
            var closestNalogaY: Int? = null
            for (naloga in naloge) {
                val (naloga_aveX, naloga_aveY) = naloga.boundingPoly.average()
                if (closestNalogaY == null) closestNalogaY = naloga_aveY
                if (teorija_maxY < naloga_aveY && naloga_aveY < closestNalogaY) {
                    closestNaloga = naloga
                    closestNalogaY = naloga_aveY
                }
            }


            if (closestNaloga != null) {
                val (naloga_minY, naloga_maxY) = closestNaloga.boundingPoly.yMinMax()
                for (annotation in annotations) {
                    val (aveX, aveY) = annotation.boundingPoly.average()
                    if (aveY in teorija_maxY until naloga_minY) {
                        teorija.add(annotation)
                        image.drawBoundBox(annotation.boundingPoly, Color.GREEN)
                    }
                }
            } else {
                for (annotation in annotations) {
                    val (aveX, aveY) = annotation.boundingPoly.average()
                    if (teorija_maxY < aveY && !footer.contains(annotation)) {
                        teorija.add(annotation)
                        image.drawBoundBox(annotation.boundingPoly, Color.GREEN)
                    }
                }
            }
            teorije.add(teorija)
        }

        image.resize(image.width / 3, image.height / 3).show()
        Thread.sleep(1000)

    }
}
