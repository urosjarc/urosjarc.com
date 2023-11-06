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

    private fun najdi_glave(slika: BufferedImage, stran: Stran): MutableList<Odsek> {
        val dol = (stran.naslov + stran.naloge).najvisjaMeja(default = stran.okvir.visina)
        val okvir = Okvir(start = Vektor(x = 0, y = 0), end = Vektor(x = stran.okvir.sirina, y = dol))
        val podnaloge = stran.podnaloge.vOkvirju(okvir = okvir)
        val anotacije = stran.anotacije.vOkvirju(okvir = okvir)

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
//        naloge.forEach { it.pododseki = this.odseki_podnalog(slika = slika, stran = stran, rob = it.okvir, tip = Odsek.Tip.PODNALOGA) }
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
                val gor = if (zgornji != null) listOf(t.povprecje.y, zgornji.povprecje.y).average() else t.start.y
                val dol = if (spodnji != null) listOf(t.povprecje.y, spodnji.povprecje.y).average() else rob.end.y

                val okvirOdseka = Okvir(start = Vektor(x = levo, y = gor.toInt()), end = Vektor(x = desno, y = dol.toInt()))

                var anotacijeOkvirja = stran.anotacije.vOkvirju(okvir = okvirOdseka)
                var najmanjsiOkvir = anotacijeOkvirja.najmanjsiOkvir

                repeat(3) {
                    anotacijeOkvirja = stran.anotacije.vOkvirju(okvir = najmanjsiOkvir)
                    najmanjsiOkvir = anotacijeOkvirja.najmanjsiOkvir
                }

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
                val levo = t.start.x
                val desno = matrika[y].getOrNull(x + 1)?.start?.x ?: rob.end.x
                val gor = vsiOkvirji.najblizjaZgornjaMeja(meja = t.start.y, default = rob.start.y)
                val dol = matrika.getOrNull(y + 1)?.toSet().najvisjaMeja(default = rob.end.y)

                //Najvecji okvir slike
                val okvirOdseka = Okvir(start = Vektor(x = levo, y = gor), end = Vektor(x = desno, y = dol))
                this.debug(slika = slika, okvirji = setOf(okvirOdseka), msg = "Najvecji okvir ($y, $x) slike...")

                //Najmanjsi okvir slike
                var anotacijeOkvirja = stran.anotacije.vOkvirju(okvir = okvirOdseka)
                var najmanjsiOkvir = anotacijeOkvirja.najmanjsiOkvir
                this.debug(slika = slika, okvirji = setOf(najmanjsiOkvir), msg = "Najmanjsi okvir ($y, $x) slike...")

//                repeat(3) {
//                    anotacijeOkvirja = stran.anotacije.robVOkvirju(okvir = najmanjsiOkvir)
//                    najmanjsiOkvir = anotacijeOkvirja.najmanjsiOkvir
//                }

                odseki.add(Odsek(okvir = najmanjsiOkvir, anotacije = anotacijeOkvirja, tip = Odsek.Tip.NALOGA))
            }
        }

        for (i in 0 until odseki.size) {
            val odsek = odseki[i]
            val okvir = odsek.okvir

            //Dobi zgornjo mejo naslednjega odseka
            var najnizja_meja = odseki.getOrNull(i + 1)?.okvir?.start?.y ?: stran.noga.first().start.y

            //Popravek zaradi vmesnih naslovov
            najnizja_meja = stran.naslov.medY(zgornja_meja = okvir.start.y, spodnja_meja = najnizja_meja).najvisjaMeja(default = najnizja_meja)

            //Popravki zaradi razsiritve anotacij
            okvir.end.y = vsiOkvirji.najblizjaZgornjaMeja(meja = najnizja_meja, default = najnizja_meja)

            this.debug(slika = slika, okvirji = setOf(okvir), msg = "Popravki $i slike...")

            val zamekljena = slika.zamegliSliko(radij = 5)

            //Popravki ce se nahaja se kaj pod anotacijami naloge (graf).
            var belaSirina = 0
            for (y in okvir.end.y..najnizja_meja) {
                var count = 0
                for (x in okvir.start.x..okvir.end.x)
                    if (!zamekljena.piksel(x = x, y = y).is_white()) count++
                if (count == 0) belaSirina++
                else belaSirina = 0
                if (belaSirina == 3) okvir.end.y = y - belaSirina
            }

            //Popravki ce se nahaja se kaj desno od anotacij naloge (graf)
            belaSirina = 0
            for (x in okvir.end.x until stran.okvir.end.x) {
                var count = 0
                for (y in okvir.start.y..okvir.end.y)
                    if (!zamekljena.piksel(x = x, y = y).is_white()) count++
                if (count == 0) belaSirina++
                else belaSirina = 0
                if (belaSirina == 3) okvir.end.x = x - belaSirina
            }

            this.debug(slika = zamekljena, okvirji = setOf(okvir), msg = "Koncni okvir $i slike...")
        }

        return odseki
    }

    fun debug(slika: BufferedImage, okvirji: Set<Okvir>, msg: String) {
//        var img = slika.kopiraj()
//        okvirji.forEach {
//            val graph = img.createGraphics()
//            graph.stroke = BasicStroke(6.0f);
//            graph.color = Color.BLACK
//            graph.drawRect(it.start.x, it.start.y, it.sirina, it.visina)
//            graph.dispose()
//        }
//
//        img = img.spremeniVelikost(w = 600, h = 1000)
//
//        val alert = Alert(null, null, ButtonType.OK)
//        alert.isResizable = true
//        val image = Image(img.inputStream)
//        val imageView = ImageView(image)
//        alert.graphic = imageView
//        alert.showAndWait()
    }
}
