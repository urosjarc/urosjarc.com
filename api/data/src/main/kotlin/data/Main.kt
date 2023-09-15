package data

import com.google.cloud.vision.v1.EntityAnnotation
import java.awt.Color
import java.io.File

fun main() {
    val omega11 = File("src/main/resources/Omega11.zip")
    for (image in zip_iterator(file = omega11, skip = 16, end = 1000)) {

        val img = image.deskew()
        val annos = google_ocr(img)

        /**
         * Parsanje footerja
         */
        val footer = mutableListOf<EntityAnnotation>()
        val lowestY = annos.maxBy { it.yMax() }.yMinMax()
        for (anno in annos) {
            val annoAve = anno.average()
            val dy = (lowestY.max - lowestY.min) / 2
            if (annoAve.y in lowestY.min - dy..lowestY.max + dy) {
                footer.add(anno)
            }
        }

        if (footer.isNotEmpty()) {
            footer.forEach { img.drawAnnotation(it, Color.BLACK) }
            img.resize(img.width / 3, img.height / 3).show("Footer")
            Thread.sleep(5000)
        }

        /**
         * Parsanje nalog
         */
        val naloge = mutableListOf<List<EntityAnnotation>>()
        for (anno in annos) {
            val pass = img.checkAnnotation(anno) { (it.is_color() || it.is_white()) && !it.is_black() }
            val hasEndDot = anno.description.endsWith(".")
            if (hasEndDot && pass) {
                var nalogeAnnos = annos  //Pridobivanje annotationov ki so rdeci in pripadajo isti vrstici in so levo od pike ter dovolj blizu
                    .filter { anno.average().y in it.yMin()..it.yMax() }
                    .filter { it.average().x < anno.average().x && it.xMax() - anno.xMin() < 20 }
                    .filter { img.checkAnnotation(it) { (it.is_color() || it.is_white()) && !it.is_black() } }
                    .sortedBy { it.average().x }.toMutableList()

                nalogeAnnos.add(anno)
                if (nalogeAnnos.map { it.area() }.sum() > 100) {
                    naloge.add(nalogeAnnos)
                }
            }
        }

        if (naloge.isNotEmpty()) {
            naloge.sortBy { it.first().average().y }
            naloge.forEach { naloga -> naloga.forEach { img.drawAnnotation(it, Color.CYAN) } }
            img.resize(img.width / 3, img.height / 3).show("Naloge")
            Thread.sleep(5000)
        }

        /**
         * Parsanje naslovov
         */
        val naslovi = mutableListOf<MutableSet<EntityAnnotation>>()
        for (anno in annos) {
            val nums = anno.description.split(".").map { it.toIntOrNull() }
            if (!nums.contains(null) && img.checkAnnotation(anno) { (it.is_color() || it.is_white()) && !it.is_black() }) {
                val deli = mutableSetOf(anno)
                for (del in annos) {
                    val delAve = del.average()
                    if (delAve.y in anno.yMin()..anno.yMax() && delAve.x > anno.xMax()) {
                        deli.add(del)
                    }
                }
                deli.toMutableList().sortBy { it.average().x }
                if (deli.map { it.area() }.sum() > 200) naslovi.add(deli)
            }
        }

        /**
         * Najdi naslov z najvecjo povrsino!
         */
        var glavniNaslov = mutableSetOf<EntityAnnotation>()
        if (naslovi.isNotEmpty()) {
            //Samo en naslov je lahko na strani in ta mora biti najvecji!
            glavniNaslov = naslovi.maxBy { it.map { it.area() }.sum() }
            glavniNaslov.forEach { img.drawAnnotation(it, Color.BLUE) }
            img.resize(img.width / 3, img.height / 3).show("Naslovi")
            Thread.sleep(5000)
        }


        /**
         * Parsanje headerja
         */
        val header = mutableListOf<EntityAnnotation>()
        val highestY = (naloge.map { it.first() } + glavniNaslov).minBy { it.yMin() }.yMin()
        for (annotation in annos) {
            val annoAve = annotation.average()
            if (annoAve.y < highestY) {
                header.add(annotation)
            }
        }

        if (header.isNotEmpty()) {
            header.forEach { img.drawAnnotation(it, Color.DARK_GRAY) }
            img.resize(img.width / 3, img.height / 3).show("Header")
            Thread.sleep(5000)
        }

        /**
         * Parsanje teorije
         */
        val teorije = mutableListOf<EntityAnnotation>()
        val naslov_maxY = glavniNaslov.maxByOrNull { it.yMax() }?.yMax()
        if (naslov_maxY != null) {
            val closestNaloga = naloge.map { it.first() }.filter { it.average().y > naslov_maxY }.minByOrNull { it.average().y }
            if (closestNaloga != null) { //Ce najbljizja naloga ni bila najdena

                img.drawAnnotation(closestNaloga, Color.RED)
                img.resize(img.width / 3, img.height / 3).show("Closest naloga")
                Thread.sleep(5000)

                val naloga_minY = closestNaloga.yMin()
                for (annotation in annos) {
                    val ave = annotation.average()
                    if (ave.y in naslov_maxY until naloga_minY) {
                        teorije.add(annotation)
                    }
                }
            } else { //Najblizja naloga ni bila najdena
                for (anno in annos) {
                    val annoAve = anno.average()
                    if (naslov_maxY < annoAve.y && !footer.contains(anno)) {
                        teorije.add(anno)
                    }
                }
            }

            if (teorije.isNotEmpty()) {
                teorije.forEach { img.drawAnnotation(it, Color.PINK) }
                img.resize(img.width / 3, img.height / 3).show("Teorija")
                Thread.sleep(5000)
            }
        }

    }
}
