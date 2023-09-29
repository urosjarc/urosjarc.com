package data.use_cases

import com.google.cloud.vision.v1.EntityAnnotation
import data.extend.*
import java.awt.Color
import java.awt.image.BufferedImage

class Procesiraj_omego_sliko {
    val footer = mutableListOf<EntityAnnotation>()
    val naloge = mutableListOf<MutableList<EntityAnnotation>>()
    var naslov = mutableListOf<EntityAnnotation>()
    val head = mutableListOf<EntityAnnotation>()
    val img: BufferedImage = BufferedImage(0, 0, 0)

    val annos: Collection<EntityAnnotation> = mutableListOf()

    fun parse_teorija() {
        /**
         * Parsanje teorije
         */
        val teorije = mutableListOf<EntityAnnotation>()
        val naslov_maxY = this.naslov.maxByOrNull { it.yMax() }?.yMax()
        if (naslov_maxY != null) {
            val closestNaloga = naloge.map { it.first() }.filter { it.average().y > naslov_maxY }.minByOrNull { it.average().y }
            if (closestNaloga != null) { //Ce najbljizja naloga ni bila najdena

                img.drawAnnotation(closestNaloga, Color.RED)
                img.resize(img.width / 3, img.height / 3).show("Closest naloga")
                Thread.sleep(2000)

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
                Thread.sleep(2000)
            }
        }
    }

    fun parse_footer() {
        val lowestY = annos.maxBy { it.yMax() }.yMinMax()
        for (anno in annos) {
            val annoAve = anno.average()
            val dy = (lowestY.max - lowestY.min) / 2
            if (annoAve.y in lowestY.min - dy..lowestY.max + dy) {
                this.footer.add(anno)
            }
        }
        if (this.footer.isNotEmpty()) {
            this.footer.forEach { img.drawAnnotation(it, Color.BLACK) }
            img.resize(img.width / 3, img.height / 3).show("Footer")
            Thread.sleep(2000)
        }
    }

    fun parse_naloge() {
        for (anno in annos) {
            val pass = img.averagePixel(anno).is_red()
            val hasEndDot = anno.description.endsWith(".")
            if (hasEndDot && pass) {
                val nalogeAnnos = annos  //Pridobivanje annotationov ki so rdeci in pripadajo isti vrstici in so levo od pike ter dovolj blizu
                    .filter { anno.average().y in it.yMin()..it.yMax() }
                    .filter { it.average().x < anno.average().x && it.xMax() - anno.xMin() < 20 }
                    .filter { img.averagePixel(it).is_red() }
                    .sortedBy { it.average().x }.toMutableList()

                nalogeAnnos.add(anno)
                val area = nalogeAnnos.map { it.area() }.sum()
                if (area in 30 * 30..300 * 50) {
                    this.naloge.add(nalogeAnnos)
                }
            }
        }
        if (this.naloge.isNotEmpty()) {
            this.naloge.sortBy { it.first().average().y }
            this.naloge.forEach { naloga -> naloga.forEach { img.drawAnnotation(it, Color.CYAN) } }
            img.resize(img.width / 3, img.height / 3).show("Naloge")
            Thread.sleep(2000)
        }
    }

    fun parse_naslov() {
        /**
         * Parsanje naslovov
         */
        val naslovi = mutableListOf<MutableList<EntityAnnotation>>()
        for (anno in annos) {
            val nums = anno.description.split(".").map { it.toIntOrNull() }
            if (!nums.contains(null) && img.averagePixel(anno).is_red()) {
                val deli = mutableListOf(anno)
                for (del in annos) {
                    val delAve = del.average()
                    if (delAve.y in anno.yMin()..anno.yMax() && delAve.x > anno.xMax()) {
                        deli.add(del)
                    }
                }
                deli.sortBy { it.average().x }
                val area = deli.map { it.area() }.sum()
                if (area in 150 * 150..1000 * 150) naslovi.add(deli)
            }
        }

        /**
         * Najdi naslov z najvecjo povrsino!
         */
        if (naslovi.isNotEmpty()) {
            //Samo en naslov je lahko na strani in ta mora biti najvecji!
            this.naslov = naslovi.maxBy { it.map { it.area() }.sum() }
            this.naslov.forEach { img.drawAnnotation(it, Color.BLUE) }
            img.resize(img.width / 3, img.height / 3).show("Naslovi")
            Thread.sleep(2000)
        }
    }

    fun parse_head() {
        val highestY = (this.naloge.map { it.first() } + this.naslov).minBy { it.yMin() }.yMin()
        for (annotation in annos) {
            val annoAve = annotation.average()
            if (annoAve.y < highestY) {
                head.add(annotation)
            }
        }

        if (head.isNotEmpty()) {
            head.forEach { img.drawAnnotation(it, Color.DARK_GRAY) }
            img.resize(img.width / 3, img.height / 3).show("Header")
            Thread.sleep(2000)
        }
    }
}
