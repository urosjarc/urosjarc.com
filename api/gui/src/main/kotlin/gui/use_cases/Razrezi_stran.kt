package gui.use_cases

import gui.domain.Odsek
import gui.domain.Okvir
import gui.domain.Stran
import gui.domain.Vektor
import gui.extend.*

class Razrezi_stran {

    fun zdaj(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()

        deli.addAll(this.najdi_glave(stran = stran))
        deli.addAll(this.najdi_naslove(stran = stran))
        deli.addAll(this.najdi_teorije(stran = stran))
        deli.addAll(this.najdi_naloge(stran = stran))

        return deli
    }

    fun najdi_glave(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        val dol = (stran.naslov + stran.naloge).najvisjaMeja(default = stran.okvir.visina)
        val okvir = Okvir(start = Vektor(x = 0, y = 0), end = Vektor(x = stran.okvir.sirina, y = dol))
        val anotacije = stran.anotacije.vOkvirju(okvir = okvir)
        if (anotacije.isEmpty()) return deli
        val pododseki = this.najdi_podnaloge(okvir = okvir, stran = stran, zacetek = null)
        deli.add(Odsek(okvir = anotacije.najmanjsiOkvir, tip = Odsek.Tip.GLAVA, anotacije = anotacije, pododseki = pododseki))
        return deli
    }

    fun najdi_teorije(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        if (stran.teorija.size > 0) {
            val okvir = stran.teorija.najmanjsiOkvir
            deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.TEORIJA, anotacije = stran.anotacije.vOkvirju(okvir = okvir)))
        }
        return deli
    }

    fun najdi_naslove(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        if (stran.naslov.size > 0) {
            val okvir = stran.naslov.najmanjsiOkvir
            deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.NASLOV, anotacije = stran.anotacije.vOkvirju(okvir = okvir)))
        }
        return deli
    }

    fun najdi_naloge(stran: Stran): MutableList<Odsek> {
        val okvirji = stran.anotacije.okvirji
        val deli = mutableListOf<Odsek>()
        val matrika = stran.naloge.matrika

        matrika.add(stran.noga.toList())

        for (y in 0 until matrika.size - 1) {
            for (x in 0 until matrika[y].size) {
                val t = matrika[y][x]
                val gor = okvirji.najblizjaZgornjaMeja(meja = t.start.y, default = 0)
                val levo = okvirji.najblizjaLevaMeja(okvir = t, default = 0)
                val desno = matrika[y].getOrNull(x + 1)?.start?.x ?: stran.okvir.end.x
                var dol = matrika[y + 1].toSet().najvisjaMeja(default = stran.okvir.end.y)

                //V primeru ce je naslov takoj za nalogo
                val naslovi = stran.naslov.medY(zgornja_meja = gor, spodnja_meja = dol)
                if (naslovi.isNotEmpty()) dol = naslovi.first().start.y
                //-------------------------------------

                val okvir = Okvir(start = Vektor(x = levo, y = gor), end = Vektor(x = desno, y = dol))
                var anotacije = stran.anotacije.vOkvirju(okvir = okvir)
                val najmanjsiOkvir = anotacije.najmanjsiOkvir
                var zRobnimiAnotacije = stran.anotacije.robVOkvirju(okvir=najmanjsiOkvir)
                val popravljenOkvir = zRobnimiAnotacije.najmanjsiOkvir
                val podnaloge = this.najdi_podnaloge(stran = stran, okvir = popravljenOkvir, zacetek = t)
                val naloga = Odsek(
                    pododseki = podnaloge,
                    okvir = najmanjsiOkvir,
                    anotacije = anotacije,
                    tip = Odsek.Tip.NALOGA
                )
                deli.add(naloga)
            }
        }

        return deli
    }

    fun najdi_podnaloge(stran: Stran, okvir: Okvir, zacetek: Okvir?): MutableList<Odsek> {
        val okvirji = stran.anotacije.okvirji
        val dodatno = stran.dodatno.vOkvirju(okvir = okvir)
        val podnaloge = stran.podnaloge.vOkvirju(okvir = okvir)
        val pododseki = mutableListOf<Odsek>()
        val matrika = podnaloge.matrika

        for (y in 0 until matrika.size) {
            for (x in 0 until matrika[y].size) {
                val t = matrika[y][x]
                val vsporedni = okvirji.enakaVrstica(okvir = t)
                val spodnji = matrika.getOrNull(y + 1)?.first()

                val dol = if (spodnji != null) okvirji.enakaVrstica(okvir = spodnji).najvisjaMeja(default = okvir.end.y) else okvir.end.y
                val gor = vsporedni.najvisji?.start?.y ?: t.start.y
                val levo = okvirji.najblizjaLevaMeja(okvir = t, default = 0)
                val desno = matrika[y].getOrNull(x + 1)?.start?.x ?: stran.okvir.end.x

                val anotacije = stran.anotacije.vOkvirju(okvir = Okvir(start = Vektor(x = levo, y = gor), end = Vektor(x = desno, y = dol)))

                pododseki.add(Odsek(okvir = anotacije.najmanjsiOkvir, anotacije = anotacije, tip = Odsek.Tip.PODNALOGA))
            }
        }

        //Ce je glava potem besedila ni
        if (zacetek == null) return pododseki

        //Ce ni podnalog vrni prazno
        val prviPododsek = matrika.firstOrNull()?.firstOrNull() ?: return pododseki

        //Ce je prva podnaloga na enaki vrstici kot besedilo
        if (prviPododsek.enakaVrstica(zacetek)) return pododseki

        //Najdi vse anotacije malce visje kot pa je velik zacetek
        val okvirjiBesedila = stran.anotacije.okvirji.medY(spodnja_meja = prviPododsek.start.y, zgornja_meja = zacetek.start.y).najmanjsiOkvir
        val anotacijeBesedila = stran.anotacije.vOkvirju(okvir = okvirjiBesedila)
        pododseki.add(0, Odsek(okvir = okvirjiBesedila, anotacije = anotacijeBesedila, tip = Odsek.Tip.NALOGA, dodatno = dodatno))
        return pododseki
    }
}
