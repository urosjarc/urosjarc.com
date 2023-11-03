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
        deli.addAll(this.najdi_odseke_naslovov(stran = stran))
        deli.addAll(this.najdi_odseke_teorij(stran = stran))
        deli.addAll(this.najdi_odseke_nalog(stran = stran))

        return deli
    }

    fun najdi_odseke_glave(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        val spodnja_meja_glave = (stran.naslov + stran.naloge).najvisjaMeja(default = stran.okvir.visina)
        val okvir = Okvir(start = Vektor(x = 0, y = 0), end = Vektor(x = stran.okvir.sirina, y = spodnja_meja_glave))
        val anotacije = stran.anotacije.vOkvirju(okvir = okvir)
        if (anotacije.isEmpty()) return deli
        val pododseki = this.najdi_odseke_podnalog(okvir = okvir, stran = stran, zacetek = null)
        deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.GLAVA, anotacije = anotacije, pododseki = pododseki))
        return deli
    }

    fun najdi_odseke_teorij(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        val okvirji = stran.anotacije.okvirji
        if (stran.teorija.size > 0) {
            val zgornja_meja = stran.teorija.najvisjaMeja(default = 0)
            val spodnja_meja = stran.teorija.najnizjaMeja(default = stran.okvir.visina)
            val najvisja_zgornja_meja = okvirji.najblizjaZgornjaMeja(meja = zgornja_meja, default = zgornja_meja)
            val najnizja_spodnja_meja = okvirji.najblizjaSpodnjaMeja(meja = spodnja_meja, default = spodnja_meja)
            val okvir = Okvir(start = Vektor(x = 0, y = najvisja_zgornja_meja), end = Vektor(x = stran.okvir.sirina, y = najnizja_spodnja_meja))
            deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.TEORIJA, anotacije = stran.anotacije.vOkvirju(okvir = okvir)))
        }
        return deli
    }

    fun najdi_odseke_naslovov(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        val okvirji = stran.anotacije.okvirji
        if (stran.naslov.size > 0) {
            val zgornja_meja = stran.naslov.najvisjaMeja(default = 0)
            val spodnja_meja = stran.naslov.najnizjaMeja(default = stran.okvir.visina)
            val najvisja_zgornja_meja = okvirji.najblizjaZgornjaMeja(meja = zgornja_meja, default = zgornja_meja)
            val najnizja_spodnja_meja = okvirji.najblizjaSpodnjaMeja(meja = spodnja_meja, default = spodnja_meja)
            val okvir = Okvir(start = Vektor(x = 0, y = najvisja_zgornja_meja), end = Vektor(x = stran.okvir.sirina, y = najnizja_spodnja_meja))
            deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.NASLOV, anotacije = stran.anotacije.vOkvirju(okvir = okvir)))
        }
        return deli
    }

    fun najdi_odseke_nalog(stran: Stran): MutableList<Odsek> {
        val okvirji = stran.anotacije.okvirji
        val deli = mutableListOf<Odsek>()
        val matrika = stran.naloge.matrika

        matrika.add(stran.noga.toList())

        for (y in 0 until matrika.size-1) {
            for (x in 0 until matrika[y].size) {
                val t = matrika[y][x]
                val gor = okvirji.najblizjaZgornjaMeja(meja = t.start.y, default = 0)
                val levo = okvirji.najblizjaLevaMeja(okvir = t, default = 0)
                val desno = matrika[y].getOrNull(x + 1)?.start?.x ?: stran.okvir.end.x
                var dol = matrika.getOrNull(y + 1)?.first()?.start?.y ?: stran.okvir.end.y

                //V primeru ce je naslov takoj za nalogo
                val naslovi = stran.naslov.medY(zgornja_meja = gor, spodnja_meja = dol)
                if (naslovi.isNotEmpty()) dol = naslovi.first().start.y
                //-------------------------------------

                val okvirNaloge = Okvir(start = Vektor(x = levo, y = gor), end = Vektor(x = desno, y = dol))
                val podnaloge = this.najdi_odseke_podnalog(stran=stran, okvir=okvirNaloge, zacetek = t)
                val naloga = Odsek(pododseki = podnaloge, okvir = okvirNaloge, anotacije = stran.anotacije.vOkvirju(okvir = okvirNaloge), tip = Odsek.Tip.NALOGA)
                deli.add(naloga)
            }
        }

        return deli
    }

    fun najdi_odseke_podnalog(stran: Stran, okvir: Okvir, zacetek: Okvir?): MutableList<Odsek> {
        val okvirji = stran.anotacije.okvirji
        val dodatno = stran.dodatno.vOkvirju(okvir = okvir)
        val podnaloge = stran.podnaloge.vOkvirju(okvir = okvir)
        val pododseki = mutableListOf<Odsek>()
        val matrika = podnaloge.matrika

        val najnizja_meja_besedila = podnaloge.najvisjaMeja(default = okvir.end.y)
        val spodnja_meja_besedila = okvirji.najblizjaZgornjaMeja(meja = najnizja_meja_besedila, default = najnizja_meja_besedila)

        for (y in 0 until matrika.size) {
            for (x in 0 until matrika[y].size) {
                val t = matrika[y][x]
                val zgornja_meje_prve_vrstice = if (zacetek == null) 0 else minOf(spodnja_meja_besedila, t.start.y)
                val levo = okvirji.najblizjaLevaMeja(okvir = t, default = 0)
                val gor = matrika.getOrNull(y - 1)?.first()?.end?.y ?: zgornja_meje_prve_vrstice
                val desno = matrika[y].getOrNull(x + 1)?.start?.x ?: okvir.end.x
                val dol = matrika.getOrNull(y + 1)?.first()?.start?.y ?: okvir.end.y
                val okvirPodnaloge = Okvir(start = Vektor(x = levo, y = gor), end = Vektor(x = desno, y = dol))
                val podnaloga = Odsek(okvir = okvirPodnaloge, anotacije = stran.anotacije.vOkvirju(okvir = okvirPodnaloge), tip = Odsek.Tip.PODNALOGA)
                pododseki.add(podnaloga)
            }
        }

        //Ce je glava potem besedila ni
        if (zacetek == null) return pododseki

        //Ce ni podnalog vrni prazno
        val prviPododsek = matrika.firstOrNull()?.firstOrNull() ?: return pododseki

        //Ce je prva podnaloga na enaki vrstici kot besedilo
        if (prviPododsek.enakaVrstica(zacetek)) return pododseki

        val okvirBesedila = Okvir(start = Vektor(x = zacetek.start.x, y = okvir.start.y), end = Vektor(x = okvir.end.x, y = najnizja_meja_besedila))
        val anotacijeBesedila = stran.anotacije.vOkvirju(okvir = okvirBesedila)
        val besedilo = Odsek(okvir = okvirBesedila, anotacije = anotacijeBesedila, tip = Odsek.Tip.NALOGA, dodatno = dodatno)
        pododseki.add(0, besedilo)

        return pododseki
    }
}
