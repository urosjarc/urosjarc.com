package gui.use_cases

import gui.domain.*
import gui.extend.averagePixel
import gui.extend.removeBorder
import gui.extend.vmes
import java.awt.image.BufferedImage
import kotlin.math.abs

class Razrezi_stran() {

    fun zdaj(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        /**
         * Glava
         */
        if (stran.glava.size > 0) {
            val maxGlava = stran.glava.maxBy { it.y_max }
            val yEnd = this.dol_najblizji(stran, start = maxGlava.y_max)
            val img = stran.slika.img.getSubimage(0, 0, stran.slika.img.width, yEnd)
            val border = img.removeBorder(50)
            deli.add(Odsek(x = border.first, y = 0 + border.first, img = border.second, anotacije = stran.glava))
        }

        /**
         * Teorija
         */
        if (stran.teorija.size > 0) {
            val minGlava = stran.teorija.minBy { it.y }
            val maxGlava = stran.teorija.maxBy { it.y_max }
            val yStart = this.gor_najblizji(stran, start = minGlava.y)
            val yEnd = this.dol_najblizji(stran, start = maxGlava.y_max)
            val img = stran.slika.img.getSubimage(0, yStart, stran.slika.img.width, yEnd)
            deli.add(Odsek(x = 0, y = yStart, img = img, anotacije = stran.teorija))
        }

        /**
         * Find footer
         */
        val nalogaY = stran.naloge.map { it.first().y }.toMutableList()
        nalogaY.add(stran.noga[0].y)

        /**
         * Naloge
         */
        for (i in 0 until nalogaY.size - 1) {
            val yStart = this.gor_najblizji(stran, start = nalogaY[i])
            val yEnd = nalogaY[i + 1].toInt()
            val img = stran.slika.img.getSubimage(0, yStart, stran.slika.img.width, abs(yStart - yEnd))
            val anotacije = stran.anotacije.filter { it.average.y.vmes(yStart, yEnd) }
            val border = img.removeBorder(50)
            deli.add(Odsek(x = border.first, y = yStart + border.first, img = border.second, anotacije = anotacije))
        }

        deli.sortBy { it.anotacije.first().y }
        deli.forEach { odsek ->
            odsek.anotacije = odsek.anotacije.map {
                it.copy(
                    y = it.y - odsek.y,
                    x = it.x - odsek.x
                )
            }
        }

        return deli
    }

    private fun gor_najblizji(stran: Stran, start: Double): Int =
        stran.anotacije.filter { it.average.y < start }.maxBy { it.average.y }.y_max.toInt()

    private fun dol_najblizji(stran: Stran, start: Double): Int =
        stran.anotacije.filter { it.average.y > start }.minBy { it.average.y }.y_max.toInt()
}
