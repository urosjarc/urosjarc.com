package gui.use_cases

import gui.domain.Odsek
import gui.domain.Okvir
import gui.domain.Stran
import gui.domain.Vektor
import gui.extend.*
import kotlin.math.abs

class Razrezi_stran {

    fun zdaj(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        val anotacije = stran.anotacije
        val okvirji = anotacije.okvirji

        /**
         * Glava
         */
        if (stran.glava.size > 0) {
            val spodnja_meja = stran.glava.najnizjaMeja(default = stran.okvir.visina)
            val najnizja_spodnja_meja = okvirji.najblizjaSpodnjaMeja(meja = spodnja_meja, default = spodnja_meja)
            val okvir = Okvir(start = Vektor(x = 0, y = 0), end = Vektor(x = stran.okvir.sirina, y = najnizja_spodnja_meja))
            deli.add(Odsek(okvir=okvir, tip = Odsek.Tip.GLAVA, anotacije = anotacije.vOkvirju(okvir=okvir)))
        }

        /**
         * Teorija
         */
        if (stran.teorija.size > 0) {
            val zgornja_meja = stran.teorija.najvisjaMeja(default = 0)
            val spodnja_meja = stran.teorija.najnizjaMeja(default = stran.okvir.visina)
            val najvisja_zgornja_meja = okvirji.najblizjaZgornjaMeja(meja = zgornja_meja, default = zgornja_meja).toInt()
            val najnizja_spodnja_meja = okvirji.najblizjaSpodnjaMeja(meja = spodnja_meja, default = spodnja_meja).toInt()
            val okvir = Okvir(start=Vektor(x = 0, y = najvisja_zgornja_meja), end=Vektor(x=stran.okvir.sirina, y=najnizja_spodnja_meja))
            deli.add(Odsek(okvir=okvir, tip = Odsek.Tip.TEORIJA, anotacije = anotacije.vOkvirju(okvir=okvir)))
        }

        /**
         * Noga
         */
        val nalogaY = stran.naloge.map { it.start.y }.toMutableList()
        nalogaY.add(stran.noga[0].start.y)
        nalogaY.sort()

        /**
         * Naloge
         */
        for (i in 0 until nalogaY.size - 1) {
            val zgornja_meja = okvirji.najblizjaZgornjaMeja(meja = nalogaY[i], default = 0)
            val spodnja_meja = nalogaY[i + 1]
            val okvir = Okvir(start= Vektor(x = 0, y = zgornja_meja), end=Vektor(x=stran.okvir.sirina, y=spodnja_meja))
            deli.add(Odsek(okvir=okvir, tip=Odsek.Tip.NALOGA, anotacije = anotacije.vOkvirju(okvir=okvir)))
        }

        return deli
    }
}
