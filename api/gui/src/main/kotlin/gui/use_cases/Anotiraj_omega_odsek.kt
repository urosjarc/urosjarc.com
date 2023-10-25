package gui.use_cases

import gui.domain.Anotacija
import gui.domain.Odsek
import gui.domain.Okvir
import gui.domain.Vektor
import gui.extend.*

class Anotiraj_omega_odsek {

    fun zdaj(odsek: Odsek): MutableList<Odsek> {
        val odseki = mutableListOf<Odsek>()
        val anotacije = odsek.anotacije
        val okvirji = anotacije.okvirji

        /**
         * Ustvari grupe
         */
        val crkaOklepaj = vseCrkeZOklepajem(anotacije)
        val grupe = mutableListOf<MutableList<Anotacija>>()
        while (crkaOklepaj.isNotEmpty()) {
            //Najdi kandidate za grupo
            val ano = crkaOklepaj.removeAt(0)
            val kandidati = mutableListOf(ano)
            for (i in 0 until crkaOklepaj.size) {
                if (crkaOklepaj[i].okvir.enakaVrstica(ano.okvir)) kandidati.add(crkaOklepaj[i])
            }
            kandidati.sortBy { it.okvir.start.x }
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
        val y_min = grupe.first().first().okvir.start.y //Todo: Kaj pa ce je teorija pred glavo?
        val okvirjiGlave = okvirji.nad(meja = y_min)
        if (anotacije.first().tip == Anotacija.Tip.NALOGA) {
            val spodnja_meja_anotacij = okvirjiGlave.najnizjaMeja(default = 0)
            val spodnja_meja = okvirji.najblizjaSpodnjaMeja(meja = spodnja_meja_anotacij, default = odsek.okvir.visina)
            val leva_meja = okvirjiGlave.levaMeja(default = 0)
            val okvir = Okvir(start = Vektor(x = leva_meja, y = 0), end = Vektor(x = odsek.okvir.end.x, y = spodnja_meja))
            odseki.add(Odsek(okvir = okvir, tip = Odsek.Tip.NALOGA, anotacije = anotacije.vOkvirju(okvir = okvir)))
        }

        /**
         * Ustvari pravilne anotacije ki vsebuje celotno nalogo
         */
        val spodnja_meja_glave = okvirjiGlave.najnizjaMeja(default = 0)
        for (y in 0 until grupe.size) {
            for (x in 0 until grupe[y].size) {
                val ano = grupe[y][x]
                val spodnja_meja_slike = odsek.okvir.visina
                val zgoraj = grupe.getOrNull(y - 1)?.okvirji.najnizjaMeja(default = spodnja_meja_glave)
                val spodaj = grupe.getOrNull(y + 1)?.okvirji.najvisjaMeja(default = spodnja_meja_slike)
                val levo = ano.okvir.start.x
                val desno = grupe[y].okvirji.najblizjaDesnaMeja(okvir = ano.okvir, default = odsek.okvir.sirina)
                val okvir = Okvir(start= Vektor(x=levo, y=zgoraj), end= Vektor(x=desno, y=spodaj))
                odseki.add(Odsek(okvir=okvir, anotacije = odsek.anotacije.vOkvirju(okvir=okvir), tip = Odsek.Tip.NALOGA))
            }
        }

        return odseki
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
