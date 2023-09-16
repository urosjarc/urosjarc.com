package data

import com.google.cloud.vision.v1.EntityAnnotation
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

data class OmegaSlika(val img: BufferedImage, val annos: Collection<EntityAnnotation>) {
    val footer = mutableListOf<EntityAnnotation>()
    val naloge = mutableListOf<MutableList<EntityAnnotation>>()
    var naslov = mutableListOf<EntityAnnotation>()
    val head = mutableListOf<EntityAnnotation>()
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
                if (nalogeAnnos.map { it.area() }.sum() > 100) {
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
                if (deli.map { it.area() }.sum() > 200) naslovi.add(deli)
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

data class Annotation(
    val ocr: EntityAnnotation,
    var img: BufferedImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB),
    val tip: Tip,
) {
    companion object {
        fun map(annos: Collection<EntityAnnotation>, tip: Tip): MutableList<Annotation> {
            return annos.map { Annotation(ocr = it, tip = tip) }.toMutableList()
        }
    }

    enum class Tip { FOOTER, NALOGA, NASLOV, HEAD }
}

data class Omega(val fileName: String, val skip: Int, val end: Int) {
    val file = File("src/main/resources/$fileName")
    val omegaParts = mutableListOf<Annotation>()

    fun parse() {
        for (image in zip_iterator(file = this.file, skip = this.skip, end = this.end)) {
            //Popravi sliko
            val img = image.deskew()

            //Ocr sliko
            val annos = google_ocr(img)
            if (annos.size < 10) break

            //Process sliko
            val omega = OmegaSlika(img = img, annos = annos)
            omega.parse_footer()
            omega.parse_naloge()
            omega.parse_naslov()
            omega.parse_head()
            omega.parse_teorija()

            val marks = mutableListOf<Annotation>()
            if (omega.naloge.isNotEmpty())
                marks += Annotation.map(omega.naloge.map { it.first() }, Annotation.Tip.NALOGA)
            if (omega.naslov.isNotEmpty())
                marks.add(Annotation(omega.naslov.first(), tip = Annotation.Tip.NASLOV))

            marks.sortBy { it.ocr.average().y }

            if (omega.head.isNotEmpty()) {
                this.omegaParts.last().img.append(img.getSubimage(0, 0, img.width, marks.first().ocr.yMin()))
            }

            for (i in 0 until marks.size - 1) {
                val curr = marks[i]
                val next = marks[i + 1]
                val dy = next.ocr.average().y - curr.ocr.average().y
                curr.img = img.getSubimage(0, curr.ocr.yMin(), img.width, dy)
                omegaParts.add(curr)
            }
        }
    }

}

fun main() {
    val omega = Omega("test.zip", 20, 40)
    omega.parse()
}
