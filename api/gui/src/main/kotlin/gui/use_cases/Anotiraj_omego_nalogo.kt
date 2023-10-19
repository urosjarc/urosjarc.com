package gui.use_cases

import gui.domain.Anotacija
import gui.domain.Naloga
import gui.domain.Odsek
import kotlin.math.abs

class Anotiraj_omego_nalogo {

    private fun kodaCrke(crka: Char): Int {
        var c = crka
        if (c == '1') c = 'l'
        return "abcdefghijklmnoprstuvz".indexOf(c)
    }

    fun zdaj(odsek: Odsek): Naloga {
        val naloga = Naloga(odsek = odsek)

        /**
         * Najdi vse oklepaje
         */
        val oklepaji = odsek.anotacije.filter { it.text == ")" }

        /**
         * Dobi vse elemente pred oklepaji
         */
        val crke_oklepaj = mutableListOf<Anotacija>()
        for (oklepaj in oklepaji) {
            val i = odsek.anotacije.indexOf(oklepaj)
            val prejsnji = odsek.anotacije[i - 1]
            if (prejsnji.text.length == 1) {
                crke_oklepaj.add(prejsnji)
            }
        }

        /**
         * Filtriraj elemente ki imajo crke v pravilnem zaporedju eno za drugo
         */
        var charIndex = this.kodaCrke(crke_oklepaj.first().text.first())
        val podnaloge = mutableListOf<Anotacija>()
        while (true) {
            val ano = crke_oklepaj.filter { this.kodaCrke(it.text.first()) == charIndex }
            if (ano.isEmpty()) break
            podnaloge.addAll(ano)
            charIndex++
        }

        /**
         * Grupiraj naloge po vrstici in stolpcu ter sortiraj po x
         */
        val grupe = mutableListOf<MutableList<Anotacija>>()
        while (podnaloge.isNotEmpty()) {
            /**
             * Ustvarjanje grupe
             */
            val ano = podnaloge.removeAt(0)
            val grupa = mutableListOf(ano)
            for (i in 0 until podnaloge.size) {
                if (podnaloge[i].average.y in ano.y..ano.y_max) {
                    grupa.add(podnaloge[i])
                }
            }

            /**
             * Sortiraj po poziciji
             */
            grupa.sortBy { it.x }

            /**
             * Odstrani vse tiste ki niso v rangu
             */
            grupa.forEach { podnaloge.remove(it) }
            val novaGrupa = mutableListOf(grupa[0])
            for (i in 1 until grupa.size) {
                if (this.kodaCrke(novaGrupa.last().text.first()) - this.kodaCrke(grupa[i].text.first()) == -1) {
                    novaGrupa.add(grupa[i])
                }
            }
            grupe.add(novaGrupa)
        }

        /**
         * Sortiraj vrstice grup
         */
        grupe.sortBy { this.kodaCrke(it.first().text.first()) }

        /**
         * Dodaj glavo ce je tip naloge
         */
        val y_min = grupe.first().first().y
        val glavaAno = odsek.anotacije.filter { it.average.y < y_min }
        if (odsek.anotacije.first().tip == Anotacija.Tip.NALOGA) {
            val y_max_zadnji = glavaAno.maxOf { it.y_max }
            val y_max = odsek.anotacije.filter { it.y > y_max_zadnji }.minOf { it.y }
            naloga.glava.add(
                Anotacija(
                    x = 0.0, y = 0.0,
                    height = y_max,
                    width = odsek.img.width.toDouble(),
                    text = "",
                    tip = Anotacija.Tip.HEAD
                )
            )
        }

        /**
         * Ustvari pravilne anotacije ki vsebuje celotno nalogo
         */
        for (y in 0 until grupe.size) {
            for (x in 0 until grupe[y].size) {
                val curr = grupe[y][x]

                //Dobi prvo zgornjo anotacijo, ali glavo drugace pa zacetek slike
                val y_min = grupe.getOrNull(y - 1)?.first()?.y_max ?: glavaAno.maxOfOrNull { it.y_max } ?: 0.0

                //Dobi prvo naslednjo ali konec slike
                val y_max = grupe.getOrNull(y + 1)?.first()?.y ?: odsek.img.height.toDouble()

                //Dobi prvo pred to anotacijo na isti visini ali zacetek slike
                val x_min = odsek.anotacije.filter { it.average.y in curr.y..curr.y_max }.filter { it.x < curr.x }.maxOfOrNull { it.x_max } ?: 0.0

                //Dobi zacetek naslednje anotacije
                val x_max = grupe[y].getOrNull(x + 1)?.x ?: odsek.img.width.toDouble()

                naloga.podnaloge.add(
                    curr.copy(
                        x = x_min,
                        y = y_min,
                        height = abs(y_max - y_min),
                        width = abs(x_max - x_min),
                    )
                )
            }
        }

        return naloga
    }
}
