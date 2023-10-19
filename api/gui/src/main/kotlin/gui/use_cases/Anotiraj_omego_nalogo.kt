package gui.use_cases

import gui.domain.Anotacija
import gui.domain.Naloga
import gui.domain.Odsek
import kotlin.math.abs

class Anotiraj_omego_nalogo {
    fun zdaj(odsek: Odsek): Naloga {
        val naloga = Naloga(odsek = odsek)

        /**
         * Dobi vse elemente ki so v stilu a), b), ...
         */
        val oklepaji = odsek.anotacije.filter { it.text == ")" }
        val crke_oklepaj = mutableListOf<Anotacija>()
        for (oklepaj in oklepaji) {
            val i = odsek.anotacije.indexOf(oklepaj)
            val prejsnji = odsek.anotacije[i - 1]
            if (prejsnji.text.length == 1) {
                crke_oklepaj.add(prejsnji)
            }
        }

        /**
         * Filtriraj elemente ki imajo pravo crko
         */
        var charIndex = 'a'.code
        val podnaloga = mutableListOf<Anotacija>()
        while (true) {
            val ano = crke_oklepaj.filter { it.text.first().code == charIndex }
            if(ano.isEmpty()) break
            podnaloga.addAll(ano)
            charIndex++
        }

        /**
         * Grupiraj naloge po vrstici in stolpcu ter sortiraj po x
         */
        val grupe = mutableListOf<MutableList<Anotacija>>()
        while (podnaloga.isNotEmpty()) {
            val ano = podnaloga.removeAt(0)
            val grupa = mutableListOf(ano)
            for (i in 0 until podnaloga.size) {
                if (podnaloga[i].average.y in ano.y..ano.y_max) {
                    grupa.add(podnaloga[i])
                }
            }
            grupa.forEach { podnaloga.remove(it) }
            grupa.sortBy { it.text.first().code }
            val novaGrupa = mutableListOf(grupa[0])
            for(i in 1 until grupa.size){
                if(grupa[i].text.first() > novaGrupa.last().text.first()){
                    novaGrupa.add(grupa[i])
                }
            }
            grupe.add(novaGrupa)
        }


        /**
         * Dodaj glavo ce je tip naloge
         */
        if (odsek.anotacije.first().tip == Anotacija.Tip.NALOGA) {
            naloga.glava.add(
                Anotacija(
                    x = 0.0, y = 0.0,
                    height = grupe.first().first().y,
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
                val y_max = grupe.getOrNull(y + 1)?.first()?.y ?: odsek.img.height.toDouble()
                val x_max = grupe[y].getOrNull(x + 1)?.x ?: odsek.img.width.toDouble()
                naloga.podnaloge.add(
                    curr.copy(
                        height = abs(y_max - curr.y),
                        width = abs(x_max - curr.x),
                    )
                )
            }
        }

        return naloga
    }
}
