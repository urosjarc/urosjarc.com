package gui.use_cases

import gui.domain.Odsek
import gui.domain.Okvir
import gui.domain.Stran
import gui.domain.Vektor
import gui.extend.*

class Razrezi_stran {

    fun zdaj(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()

        deli.addAll(this.najdi_odseke_glave(stran = stran))
        deli.addAll(this.najdi_odseke_teorij(stran = stran))
        deli.addAll(this.najdi_odseke_nalog(stran = stran))

        return deli
    }

    fun najdi_odseke_glave(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        val spodnja_meja_glave = (stran.naslov + stran.naloge).najvisjaMeja(default = stran.okvir.visina)
        val okvir = Okvir(start = Vektor(x = 0, y = 0), end = Vektor(x = stran.okvir.sirina, y = spodnja_meja_glave))
        val pododseki = this.najdi_odseke_naloge(okvir = okvir, stran = stran)
        deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.GLAVA, anotacije = stran.anotacije.vOkvirju(okvir = okvir), pododseki = pododseki))
        return deli
    }

    fun najdi_odseke_teorij(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        val okvirji = stran.anotacije.okvirji
        if (stran.teorija.size > 0) {
            val zgornja_meja = stran.teorija.najvisjaMeja(default = 0)
            val spodnja_meja = stran.teorija.najnizjaMeja(default = stran.okvir.visina)
            val najvisja_zgornja_meja = okvirji.najblizjaZgornjaMeja(meja = zgornja_meja, default = zgornja_meja).toInt()
            val najnizja_spodnja_meja = okvirji.najblizjaSpodnjaMeja(meja = spodnja_meja, default = spodnja_meja).toInt()
            val okvir = Okvir(start = Vektor(x = 0, y = najvisja_zgornja_meja), end = Vektor(x = stran.okvir.sirina, y = najnizja_spodnja_meja))
            deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.TEORIJA, anotacije = stran.anotacije.vOkvirju(okvir = okvir)))
        }
        return deli
    }

    fun najdi_odseke_nalog(stran: Stran): MutableList<Odsek> {
        val okvirji = stran.anotacije.okvirji
        val deli = mutableListOf<Odsek>()

        val nalogaY = stran.naloge.map { it.start.y }.toMutableList()
        nalogaY.add(stran.noga.first().start.y)
        nalogaY.sort()

        /**
         * Naloge
         */
        for (i in 0 until nalogaY.size - 1) {
            val zgornja_meja = okvirji.najblizjaZgornjaMeja(meja = nalogaY[i], default = 0)
            var spodnja_meja = nalogaY[i + 1]

            //V primeru ce je glava takoj za nalogo
            val glaveMedMejo = stran.naslov.medY(zgornja_meja = zgornja_meja, spodnja_meja = spodnja_meja)
            if (glaveMedMejo.isNotEmpty()) spodnja_meja = glaveMedMejo.first().start.y
            //-------------------------------------

            val okvir = Okvir(start = Vektor(x = 0, y = zgornja_meja), end = Vektor(x = stran.okvir.sirina, y = spodnja_meja))
            val pododseki = this.najdi_odseke_naloge(okvir = okvir, stran = stran)
            deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.NALOGA, anotacije = stran.anotacije.vOkvirju(okvir = okvir), pododseki = pododseki))
        }

        return deli
    }

    fun najdi_odseke_naloge(okvir: Okvir, stran: Stran): MutableList<Odsek> {
        val dodatno = stran.dodatno.vOkvirju(okvir = okvir)
        val podnaloge = stran.podnaloge.vOkvirju(okvir = okvir)
        val pododseki = mutableListOf<Odsek>()

        val spodnja_meja_besedila = podnaloge.najvisjaMeja(default = okvir.end.y)
        val okvirBesedila = Okvir(start = okvir.start, end = Vektor(x = okvir.end.x, y = spodnja_meja_besedila))
        val anotacijeBesedila = stran.anotacije.vOkvirju(okvir = okvirBesedila)
        if (anotacijeBesedila.isNotEmpty()) { //V primeru če je glava!
            val besedilo = Odsek(okvir = okvirBesedila, anotacije = anotacijeBesedila, tip = Odsek.Tip.NALOGA, dodatno = dodatno)
            pododseki.add(besedilo)
        }

        val matrika = podnaloge.matrika
        for (y in 0 until matrika.size) {
            for (x in 0 until matrika[y].size) {
                val t = matrika[y][x]
                val gor = matrika.getOrNull(y - 1)?.first()?.end?.y ?: anotacijeBesedila.okvirji.najnizjaMeja(default = 0)
                val desno = matrika[y].getOrNull(x + 1)?.start?.x ?: okvir.end.x
                val dol = matrika.getOrNull(y + 1)?.first()?.start?.y ?: okvir.end.y
                val okvir = Okvir(start = Vektor(x = t.start.x, y = gor), end = Vektor(x = desno, y = dol))
                val podnaloga = Odsek(okvir = okvir, anotacije = stran.anotacije.vOkvirju(okvir = okvir), tip = Odsek.Tip.PODNALOGA)
                pododseki.add(podnaloga)
            }
        }

        return pododseki
    }
}
