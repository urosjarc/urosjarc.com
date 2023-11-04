package gui.use_cases

import gui.domain.Odsek
import gui.domain.Okvir
import gui.domain.Stran
import gui.domain.Vektor
import gui.extend.*
import java.awt.image.BufferedImage

class Razrezi_stran {
    fun zdaj(slika: BufferedImage, stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()

        deli.addAll(this.najdi_glave(slika = slika, stran = stran))
        deli.addAll(this.najdi_naslove(stran = stran))
        deli.addAll(this.najdi_teorije(stran = stran))
        deli.addAll(this.najdi_naloge(slika = slika, stran = stran))

        return deli.sortedBy { it.okvir.start.y }.toMutableList()
    }

    fun najdi_glave(slika: BufferedImage, stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        val najnizja_meja = (stran.naslov + stran.naloge).najvisjaMeja(default = stran.okvir.visina)
        val dol = stran.okvirji.najblizjaZgornjaMeja(meja = najnizja_meja, default = najnizja_meja)
        val okvir = Okvir(start = Vektor(x = 0, y = 0), end = Vektor(x = stran.okvir.sirina, y = dol))
        val anotacije = stran.anotacije.vOkvirju(okvir = okvir)
        if (anotacije.isEmpty()) return deli
        val pododseki = this.najdi_podnaloge(slika = slika, rob = okvir, stran = stran, zacetek = null)
        deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.GLAVA, anotacije = anotacije, pododseki = pododseki))
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

    fun najdi_naloge(slika: BufferedImage, stran: Stran): MutableList<Odsek> {
        val naloge = this.odseki_matrike(slika = slika, stran = stran, rob = slika.okvir, tip = Odsek.Tip.NALOGA)
        naloge.forEach {
            val zacetek = stran.naloge.vOkvirju(it.okvir).firstOrNull()
            it.pododseki = this.najdi_podnaloge(slika = slika, stran = stran, rob = it.okvir, zacetek = zacetek)
        }
        return naloge
    }

    fun najdi_podnaloge(slika: BufferedImage, stran: Stran, rob: Okvir, zacetek: Okvir?): MutableList<Odsek> {
        val pododseki = odseki_matrike(slika = slika, stran = stran, rob = rob, tip = Odsek.Tip.PODNALOGA)

        //Ce je glava potem besedila ni
        if (zacetek == null) return pododseki

        //Ce ni podnalog vrni prazno
        val prviPododsek = pododseki.firstOrNull() ?: return pododseki

        //Ce je prva podnaloga na enaki vrstici kot besedilo
        if (prviPododsek.okvir.enakaVrstica(zacetek)) return pododseki

        return pododseki
    }

    fun odseki_matrike(
        slika: BufferedImage,
        stran: Stran,
        rob: Okvir,
        tip: Odsek.Tip,
    ): MutableList<Odsek> {
        val vsiOkvirji = stran.okvirji
        val okvirji = if (tip == Odsek.Tip.NALOGA) stran.naloge + stran.noga else stran.podnaloge
        val odseki = mutableListOf<Odsek>()
        val matrika = okvirji.matrika

        for (y in 0 until matrika.size - 1) {
            for (x in 0 until matrika[y].size) {
                val t = matrika[y][x]
                val levo = vsiOkvirji.najblizjaLevaMeja(okvir = t, default = 0)
                val desno = matrika[y].getOrNull(x + 1)?.start?.x ?: rob.end.x
                val gor = vsiOkvirji.najblizjaZgornjaMeja(meja = t.start.y, default = 0)
                var dol = matrika[y + 1].toSet().najvisjaMeja(default = rob.end.y)

                val okvirOdseka = Okvir(start = Vektor(x = levo, y = gor), end = Vektor(x = desno, y = dol))
                var anotacijeOkvirja = stran.anotacije.vOkvirju(okvir = okvirOdseka)
                var najmanjsiOkvir = anotacijeOkvirja.najmanjsiOkvir

                repeat(3) {
                    anotacijeOkvirja = stran.anotacije.robVOkvirju(okvir = najmanjsiOkvir)
                    najmanjsiOkvir = anotacijeOkvirja.najmanjsiOkvir
                }

                odseki.add(Odsek(okvir = najmanjsiOkvir, anotacije = anotacijeOkvirja, tip = tip))
            }
        }

        for (i in 0 until odseki.size) {
            val odsek = odseki[i]
            val okvir = odsek.okvir

            //Dobi zgornjo mejo naslednjega odseka
            var najnizja_meja = odseki.getOrNull(i + 1)?.okvir?.start?.y ?: stran.noga.first().start.y

            //Popravek zaradi vmesnih naslovov
            najnizja_meja = stran.naslov.medY(zgornja_meja = odsek.okvir.start.y, spodnja_meja = najnizja_meja).najvisjaMeja(default = najnizja_meja)

            //Popravki zaradi razsiritve anotacij
            odsek.okvir.end.y = vsiOkvirji.najblizjaZgornjaMeja(meja = najnizja_meja, default = najnizja_meja)

            //Popravki ce se nahaja se kaj pod anotacijami naloge graf.
            val robovi = odsek.okvir
            var belaSirina = 0
            for (y in robovi.end.y..najnizja_meja) {
                var count = 0
                for (x in robovi.start.x..robovi.end.x)
                    if (!slika.piksel(x = x, y = y).is_white()) count++
                if (count == 0) belaSirina++
                else belaSirina = 0
                if (belaSirina == 3) odsek.okvir.end.y = y - belaSirina
            }
        }

        return odseki
    }
}
