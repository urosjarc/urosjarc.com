package gui.domain

import java.awt.image.BufferedImage
import kotlin.math.abs

data class Stran(
    val slika: Slika,
    val anotacije: List<Anotacija>,
    val glava: MutableList<Anotacija> = mutableListOf(),
    var naslov: MutableList<Anotacija> = mutableListOf(),
    val teorija: MutableList<Anotacija> = mutableListOf(),
    val naloge: MutableList<MutableList<Anotacija>> = mutableListOf(),
    val noga: MutableList<Anotacija> = mutableListOf(),
) {
    fun init() {
        this.noga.forEach { it.tip = Anotacija.Tip.FOOTER }
        this.naloge.forEach { it.forEach { it.tip = Anotacija.Tip.NALOGA } }
        this.naslov.forEach { it.tip = Anotacija.Tip.NASLOV }
        this.glava.forEach { it.tip = Anotacija.Tip.HEAD }
        this.teorija.forEach { it.tip = Anotacija.Tip.TEORIJA }
    }

    fun dodaj(ano: List<Anotacija>, tip: Anotacija.Tip) {
        when (tip) {
            Anotacija.Tip.NEZNANO -> {}
            Anotacija.Tip.HEAD -> this.glava.addAll(ano)
            Anotacija.Tip.NASLOV -> this.naslov.addAll(ano)
            Anotacija.Tip.TEORIJA -> this.teorija.addAll(ano)
            Anotacija.Tip.NALOGA -> this.naloge.add(ano.toMutableList())
            Anotacija.Tip.FOOTER -> this.noga.addAll(ano)
        }
        this.init()
    }

    fun odstrani(ano: List<Anotacija>) {
        ano.forEach {
            when (it.tip) {
                Anotacija.Tip.NEZNANO -> {}
                Anotacija.Tip.HEAD -> this.glava.remove(it)
                Anotacija.Tip.NASLOV -> this.naslov.remove(it)
                Anotacija.Tip.TEORIJA -> this.teorija.remove(it)
                Anotacija.Tip.NALOGA -> this.naloge.forEach { lit -> lit.remove(it) }
                Anotacija.Tip.FOOTER -> this.noga.remove(it)
            }
        }
        this.init()
    }


    fun razrezi(): MutableList<BufferedImage> {
        val deli = mutableListOf<BufferedImage>()
        /**
         * Glava
         */
        if (this.glava.size > 0) {
            val maxGlava = this.glava.maxBy { it.y_max }
            val img = this.slika.img.getSubimage(0, 0, slika.img.width, maxGlava.y_max.toInt())
            deli.add(img)
        }

        /**
         * Teorija
         */
        if (this.teorija.size > 0) {
            val minGlava = this.teorija.minBy { it.y }
            val maxGlava = this.teorija.maxBy { it.y_max }
            val img = this.slika.img.getSubimage(0, minGlava.y.toInt(), slika.img.width, abs(maxGlava.y_max - minGlava.y).toInt())
            deli.add(img)
        }

        /**
         * Find footer
         */
        val nalogaY = this.naloge.map { it.first().y }.toMutableList()
        nalogaY.add(this.noga[0].y)

        /**
         * Naloge
         */
        for (i in 0 until nalogaY.size - 1) {
            val yStart = nalogaY[i].toInt()
            val yEnd = nalogaY[i + 1]
            val zgornjaMeja = this.anotacije.filter { it.average.y < yStart }.maxBy { it.average.y }.y_max.toInt()
            val img = this.slika.img.getSubimage(0, zgornjaMeja, slika.img.width, abs(yStart - zgornjaMeja))
            deli.add(img)
        }

        return deli
    }

    override fun toString(): String {
        return "AnotacijeStrani(naloge=${this.naloge.size}, noga=${this.noga.size}, naslov=${this.naslov.size}, glava=${this.glava.size}, teorija=${this.teorija.size})"
    }
}
