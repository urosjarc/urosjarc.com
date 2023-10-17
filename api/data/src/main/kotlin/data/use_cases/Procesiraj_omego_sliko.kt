package data.use_cases

import data.domain.Anotacija
import data.domain.AnotacijeStrani
import data.extend.averagePixel
import data.extend.vmes
import java.awt.image.BufferedImage

class Procesiraj_omego_sliko {

    fun zdaj(img: BufferedImage, annos: List<Anotacija>): AnotacijeStrani {
        val slika = AnotacijeStrani()

        this.parse_footer(slika, annos)
        this.parse_naloge(img, slika, annos)
        this.parse_naslov(img, slika, annos)
        this.parse_head(slika, annos)
        this.parse_teorija(slika, annos)

        slika.init()
        return slika
    }


    fun parse_teorija(slika: AnotacijeStrani, annos: List<Anotacija>) {
        /**
         * Parsanje teorije
         */
        val naslov_maxY = slika.naslov.maxByOrNull { it.y_max }?.y_max
        if (naslov_maxY != null) {
            val closestNaloga = slika.naloge.map { it.first() }.filter { it.average.y > naslov_maxY }.minByOrNull { it.average.y }
            if (closestNaloga != null) { //Ce najbljizja naloga ni bila najdena
                val naloga_minY = closestNaloga.y
                for (annotation in annos) {
                    val ave = annotation.average
                    if (ave.y.vmes(naslov_maxY, naloga_minY)) {
                        slika.teorija.add(annotation)
                    }
                }
            } else { //Najblizja naloga ni bila najdena
                for (anno in annos) {
                    val annoAve = anno.average
                    if (naslov_maxY < annoAve.y && !slika.noga.contains(anno)) {
                        slika.teorija.add(anno)
                    }
                }
            }
        }
    }

    fun parse_footer(slika: AnotacijeStrani, annos: List<Anotacija>) {
        val lowestY = annos.maxBy { it.y_max }
        for (anno in annos) {
            val annoAve = anno.average
            val dy = lowestY.height / 2
            if (annoAve.y.vmes(lowestY.y - dy, lowestY.y_max + dy)) {
                slika.noga.add(anno)
            }
        }
    }

    fun parse_naloge(img: BufferedImage, slika: AnotacijeStrani, annos: List<Anotacija>) {
        for (anno in annos) {
            val pass = img.averagePixel(anno).is_red()
            val hasEndDot = anno.text.endsWith(".")
            if (hasEndDot && pass) {
                val nalogeAnnos = annos  //Pridobivanje annotationov ki so rdeci in pripadajo isti vrstici in so levo od pike ter dovolj blizu
                    .filter { anno.average.y.vmes(it.y, it.y_max) }
                    .filter { it.average.x < anno.average.x && it.x_max - anno.x < 20 }
                    .filter { img.averagePixel(it).is_red() }
                    .sortedBy { it.average.x }.toMutableList()

                nalogeAnnos.add(anno)
                val area = nalogeAnnos.map { it.area }.sum()
                if (area.vmes(30 * 30, 300 * 50)) {
                    slika.naloge.add(nalogeAnnos)
                }
            }
        }
        if (slika.naloge.isNotEmpty()) {
            slika.naloge.sortBy { it.first().average.y }
        }
    }

    fun parse_naslov(img: BufferedImage, slika: AnotacijeStrani, annos: List<Anotacija>) {
        /**
         * Parsanje naslovov
         */
        val naslovi = mutableListOf<MutableList<Anotacija>>()
        for (anno in annos) {
            val nums = anno.text.split(".").map { it.toIntOrNull() }
            if (!nums.contains(null) && img.averagePixel(anno).is_red()) {
                val deli = mutableListOf(anno)
                for (del in annos) {
                    val delAve = del.average
                    if (delAve.y.vmes(anno.y, anno.y_max) && delAve.x > anno.x_max) {
                        deli.add(del)
                    }
                }
                deli.sortBy { it.average.x }
                val area = deli.map { it.area }.sum()
                if (area.vmes(150 * 150, 1000 * 150)) naslovi.add(deli)
            }
        }

        /**
         * Najdi naslov z najvecjo povrsino!
         */
        if (naslovi.isNotEmpty()) {
            //Samo en naslov je lahko na strani in ta mora biti najvecji!
            slika.naslov = naslovi.maxBy { it.map { it.area }.sum() }
        }
    }

    fun parse_head(slika: AnotacijeStrani, annos: List<Anotacija>) {
        val highest = slika.naloge.map { it.first() } + slika.naslov
        if (highest.isEmpty()) return
        val highestY = highest.minBy { it.y }.y
        for (annotation in annos) {
            val annoAve = annotation.average
            if (annoAve.y < highestY) {
                slika.glava.add(annotation)
            }
        }
    }

}
