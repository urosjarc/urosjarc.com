package gui.use_cases

import gui.domain.Odsek
import gui.domain.Okvir
import gui.domain.Stran
import gui.domain.Vektor
import gui.extend.*
import java.awt.image.BufferedImage


class Razrezi_stran {
    var debug = mutableListOf<Okvir>()

    fun zdaj(slika: BufferedImage, stran: Stran): MutableList<Odsek> {
        this.debug = mutableListOf()

        val deli = mutableListOf<Odsek>()

        val meglaImg = slika.zamegliSliko(radij = 6)
        deli.addAll(this.najdi_glave(slika = meglaImg, stran = stran))
        deli.addAll(this.najdi_naslove(stran = stran))
        deli.addAll(this.najdi_teorije(stran = stran))
        deli.addAll(this.najdi_naloge(slika = meglaImg, stran = stran))

        return deli.sortedBy { it.okvir.start.y }.toMutableList()
    }

    private fun najdi_glave(slika: BufferedImage, stran: Stran): MutableList<Odsek> {
        val dol = (stran.naslov + stran.naloge).najvisjaMeja(default = stran.okvir.visina)
        val okvir = Okvir(start = Vektor(x = 0, y = 0), end = Vektor(x = stran.okvir.sirina, y = dol))
        val podnaloge = stran.podnaloge.vOkvirju(okvir = okvir)
        val anotacije = stran.anotacije.vOkvirju(okvir = okvir)

        //Ce ni anotacij potem ni glava
        if (anotacije.isEmpty()) return mutableListOf()

        val najmanjsiOkvir = anotacije.najmanjsiOkvir
        val odsek = Odsek(okvir = najmanjsiOkvir, tip = Odsek.Tip.GLAVA, anotacije = anotacije)
        if (podnaloge.isNotEmpty())
            odsek.pododseki = this.odseki_podnalog(slika = slika, stran = stran, rob = najmanjsiOkvir, tip = Odsek.Tip.PODNALOGA)

        return mutableListOf(odsek)
    }

    private fun najdi_teorije(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        if (stran.teorija.size > 0) {
            val okvir = stran.teorija.najmanjsiOkvir
            deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.TEORIJA, anotacije = stran.anotacije.vOkvirju(okvir = okvir)))
        }
        return deli
    }

    private fun najdi_naslove(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        if (stran.naslov.size > 0) {
            val okvir = stran.naslov.najmanjsiOkvir
            deli.add(Odsek(okvir = okvir, tip = Odsek.Tip.NASLOV, anotacije = stran.anotacije.vOkvirju(okvir = okvir)))
        }
        return deli
    }

    private fun najdi_naloge(slika: BufferedImage, stran: Stran): MutableList<Odsek> {
        val naloge = this.odseki_nalog(slika = slika, stran = stran, rob = slika.okvir)
        naloge.forEach { it.pododseki = this.odseki_podnalog(slika = slika, stran = stran, rob = it.okvir, tip = Odsek.Tip.PODNALOGA) }
        return naloge
    }

    private fun odseki_podnalog(slika: BufferedImage, stran: Stran, rob: Okvir, tip: Odsek.Tip): MutableList<Odsek> {
        val odseki = mutableListOf<Odsek>()
        val matrika = stran.podnaloge.vOkvirju(okvir = rob).matrika

        for (y in 0 until matrika.size) {
            for (x in 0 until matrika[y].size) {
                val t = matrika[y][x]
                val zgornji = matrika.getOrNull(y - 1)?.first()
                val spodnji = matrika.getOrNull(y + 1)?.first()
                val desno = matrika[y].getOrNull(x + 1)?.start?.x ?: rob.end.x
                val levo = t.start.x
                val gor = if (zgornji != null) listOf(t.povprecje.y, zgornji.povprecje.y).average().toInt() else t.start.y
                val dol = if (spodnji != null) listOf(t.povprecje.y, spodnji.povprecje.y).average().toInt() else rob.end.y

                val okvirOdseka = Okvir(start = Vektor(x = levo, y = gor.toInt()), end = Vektor(x = desno, y = dol))
                this.log(okvirOdseka)

                var anotacijeOkvirja = stran.anotacije.vOkvirju(okvir = okvirOdseka)
                var najmanjsiOkvir = anotacijeOkvirja.najmanjsiOkvir
                this.log(najmanjsiOkvir)

                repeat(3) {
                    anotacijeOkvirja = stran.anotacije.vOkvirju(okvir = najmanjsiOkvir)
                    najmanjsiOkvir = anotacijeOkvirja.najmanjsiOkvir
                }
                this.log(najmanjsiOkvir)

                //Razsiri do beline
                this.razsiri_do_beline(slika = slika, okvir = najmanjsiOkvir, spodnja_meja = dol, desna_meja = desno)

                odseki.add(Odsek(okvir = najmanjsiOkvir, anotacije = anotacijeOkvirja, tip = Odsek.Tip.PODNALOGA))
            }
        }

        return odseki
    }

    private fun odseki_nalog(slika: BufferedImage, stran: Stran, rob: Okvir): MutableList<Odsek> {
        val odseki = mutableListOf<Odsek>()
        val vsiOkvirji = stran.okvirji.vOkvirju(okvir = rob)
        val matrika = (stran.naloge + stran.noga).matrika

        for (y in 0 until matrika.size - 1) {
            for (x in 0 until matrika[y].size) {
                val t = matrika[y][x]

                //Dobi najvecji kvadrat
                val levo = t.start.x
                val desno = matrika[y].getOrNull(x + 1)?.start?.x ?: rob.end.x
                val gor = vsiOkvirji.najblizjaZgornjaMeja(meja = t.start.y, default = rob.start.y)
                var dol = matrika.getOrNull(y + 1)?.toSet().najvisjaMeja(default = rob.end.y)

                //Popravek zaradi vmesnih naslovov
                dol = stran.naslov.medY(zgornja_meja = gor, spodnja_meja = dol).najvisjaMeja(default = dol)

                //Najvecji okvir slike
                val okvirOdseka = Okvir(start = Vektor(x = levo, y = gor), end = Vektor(x = desno, y = dol))
                this.log(okvirOdseka)

                //Najmanjsi okvir slike
                var anotacijeOkvirja = stran.anotacije.vOkvirju(okvir = okvirOdseka)
                var najmanjsiOkvir = anotacijeOkvirja.najmanjsiOkvir
                this.log(najmanjsiOkvir)

                //Popravi zaradi strlecih anotacij
                repeat(3) {
                    anotacijeOkvirja = stran.anotacije.vOkvirju(okvir = najmanjsiOkvir)
                    najmanjsiOkvir = anotacijeOkvirja.najmanjsiOkvir
                }

                //Razsiri do beline
                this.razsiri_do_beline(slika = slika, okvir = najmanjsiOkvir, spodnja_meja = dol, desna_meja = desno)

                odseki.add(Odsek(okvir = najmanjsiOkvir, anotacije = anotacijeOkvirja, tip = Odsek.Tip.NALOGA))
            }
        }

        return odseki
    }

    fun razsiri_do_beline(slika: BufferedImage, okvir: Okvir, spodnja_meja: Int, desna_meja: Int) {
        //Popravki ce se nahaja se kaj pod anotacijami naloge (graf).
        var belaSirina = 0
        for (y in okvir.end.y..spodnja_meja) {
            var count = 0
            for (x in okvir.start.x..okvir.end.x)
                if (!slika.piksel(x = x, y = y).is_white()) count++
            if (count == 0) belaSirina++
            else belaSirina = 0
            if (belaSirina == 3) okvir.end.y = y - belaSirina
        }
        this.log(okvir)

        //Popravki ce se nahaja se kaj desno od anotacij naloge (graf)
        belaSirina = 0
        for (x in okvir.end.x until desna_meja) {
            var count = 0
            for (y in okvir.start.y..okvir.end.y)
                if (!slika.piksel(x = x, y = y).is_white()) count++
            if (count == 0) belaSirina++
            else belaSirina = 0
            if (belaSirina == 3) okvir.end.x = x - belaSirina
        }
        this.log(okvir)
    }

    fun log(okvir: Okvir) {
        this.debug.add(okvir.copy())
    }
}
