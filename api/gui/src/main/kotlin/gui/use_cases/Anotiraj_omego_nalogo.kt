package gui.use_cases

import gui.domain.Anotacija
import gui.domain.Naloga
import gui.domain.Odsek
import gui.extend.*

class Anotiraj_omego_nalogo {

    fun zdaj(odsek: Odsek): Naloga {
        val naloga = Naloga(odsek = odsek)
        val crkaOklepaj = vseCrkeZOklepajem(odsek.anotacije)

        /**
         * Ustvari grupe
         */
        val grupe = mutableListOf<MutableList<Anotacija>>()
        while (crkaOklepaj.isNotEmpty()) {
            //Najdi kandidate za grupo
            val ano = crkaOklepaj.removeAt(0)
            val kandidati = mutableListOf(ano)
            for (i in 0 until crkaOklepaj.size) {
                if (crkaOklepaj[i].vzporedna(ano)) kandidati.add(crkaOklepaj[i])
            }
            kandidati.sortBy { it.x }
            kandidati.forEach { crkaOklepaj.remove(it) }

            //Odstrani neprimerne kandidate
            val grupa = mutableListOf(kandidati[0])
            for (i in 1 until kandidati.size) {
                if (this.slKoda(grupa.last().prvaCrka) - this.slKoda(kandidati[i].prvaCrka) == -1) {
                    grupa.add(kandidati[i])
                }
            }
            grupe.add(grupa)
        }

        /**
         * Sortiraj vrstice grup
         */
        grupe.sortBy { this.slKoda(it.first().prvaCrka) }

        /**
         * Dodaj glavo ce je tip naloge
         */
        val y_min = grupe.first().first().y
        val anotacijeGlave = odsek.anotacije.nad(meja = y_min)
        if (odsek.anotacije.first().tip == Anotacija.Tip.NALOGA) {
            val spodnja_meja_anotacij = anotacijeGlave.najnizjaMeja(default = 0.0)
            val spodnja_meja = odsek.anotacije.najblizjaSpodnjaMeja(meja = spodnja_meja_anotacij, default = odsek.visina)
            val leva_meja = anotacijeGlave.levaMeja(default = 0.0)

            naloga.glava.add(
                Anotacija(
                    x = leva_meja, y = 0.0,
                    height = spodnja_meja,
                    width = odsek.sirina - leva_meja,
                    text = "",
                    tip = Anotacija.Tip.HEAD
                )
            )
        }

        /**
         * Ustvari pravilne anotacije ki vsebuje celotno nalogo
         */
        val spodnja_meja_glave = anotacijeGlave.najnizjaMeja(default = 0.0)
        for (y in 0 until grupe.size) {
            for (x in 0 until grupe[y].size) {
                val curr = grupe[y][x]
                val spodnja_meja_slike = odsek.visina
                val zgoraj = grupe.getOrNull(y - 1).najnizjaMeja(default = spodnja_meja_glave)
                val spodaj = grupe.getOrNull(y + 1).najvisjaMeja(default = spodnja_meja_slike)
                val levo = curr.x
                val desno = grupe[y].najblizjaDesnaMeja(ano = curr, default = odsek.sirina)

                naloga.podnaloge.add(
                    curr.copy(
                        x = levo,
                        y = zgoraj,
                        height = spodaj - zgoraj,
                        width = desno - levo,
                    )
                )
            }
        }

        return naloga
    }

    private fun vseCrkeZOklepajem(anotacije: List<Anotacija>): MutableList<Anotacija> {
        val oklepaji = anotacije.filter { it.text == ")" }

        val returned = mutableListOf<Anotacija>()
        for (oklepaj in oklepaji) {
            val i = anotacije.indexOf(oklepaj)
            val prejsnji = anotacije[i - 1]
            if (prejsnji.text.length == 1) returned.add(prejsnji)
        }
        return returned
    }

    private fun slKoda(crka: Char): Int {
        var c = crka
        if (c == '1') c = 'l' //Google prepozna l kot 1 :(
        return "abcdefghijklmnoprstuvz".indexOf(c)
    }

}
