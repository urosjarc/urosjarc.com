package gui.use_cases

import gui.domain.Anotacija
import gui.domain.Okvir
import gui.domain.Stran
import gui.extend.*
import java.awt.image.BufferedImage


class Anotiraj_omego_stran {

    fun zdaj(img: BufferedImage, anotacije: List<Anotacija>): Stran {
        val stran = this.ustvari_stran(img = img, anos = anotacije)
        this.dodaj_anotacije_noge(stran = stran, anos = anotacije.toSet())
        this.dodaj_anotacije_nalog(img = img, stran = stran, anos = anotacije.toSet())
        this.dodaj_anotacije_podnalog(stran = stran, anos = anotacije)
        this.dodaj_anotacije_naslovov(img = img, stran = stran, anos = anotacije.toSet())
        this.dodaj_anotacije_teorij(img = img, stran = stran, anos = anotacije.toSet())
        return stran
    }

    fun ustvari_stran(img: BufferedImage, anos: List<Anotacija>): Stran {
        //Odstrani vse anotacije ki so manjse od dovoljene
        val anotacije = anos
            .filter { it.okvir.povrsina > 8 * 8 && it.okvir.visina < 70 }
            .filter { img.steviloPikslov(okvir = it.okvir, countOn = { p -> p.is_black() }) > 8 }.toSet()
        return Stran(okvir = img.okvir, anotacije = anotacije)
    }

    fun dodaj_anotacije_teorij(img: BufferedImage, stran: Stran, anos: Set<Anotacija>) {
        if (stran.naslov.isNotEmpty()) {
            val zgornja_meja = stran.naslov.najnizjaMeja(default = img.height)
            val najnizja_spodnja_meja = stran.noga.najvisjaMeja(default = img.height)
            val spodnja_meja = stran.naloge.najblizjaSpodnjaMeja(meja = zgornja_meja, default = najnizja_spodnja_meja)
            val okvirji = anos.okvirji.medY(zgornja_meja = zgornja_meja, spodnja_meja = spodnja_meja)
            stran.teorija.addAll(okvirji)
            stran.naloge.removeIf { okvirji.contains(it) }
            stran.podnaloge.removeIf { okvirji.contains(it) }
        }
    }

    fun dodaj_anotacije_noge(stran: Stran, anos: Set<Anotacija>) {
        val najnizji = anos.okvirji.najnizji
        if (najnizji != null) {
            val y = najnizji.povprecje.y
            val dy = najnizji.visina / 2
            stran.noga.addAll(anos.okvirji.medY(zgornja_meja = y - dy, spodnja_meja = y + dy))
        }
    }

    fun dodaj_anotacije_nalog(img: BufferedImage, stran: Stran, anos: Set<Anotacija>) {
        for (ano in anos) {
            val isRed = img.povprecenPiksel(ano.okvir).is_red()
            val isInt = ano.text.removeSuffix(".").toIntOrNull() != null
            val isFloat = ano.text.toFloatOrNull() != null
            val hasEndDot = ano.text.endsWith('.')
            if (isRed && (isInt || (isFloat && hasEndDot))) stran.naloge.add(ano.okvir)
        }
    }

    fun dodaj_anotacije_naslovov(img: BufferedImage, stran: Stran, anos: Set<Anotacija>) {
        val naslovi = mutableSetOf<Okvir>()
        for (ano in anos) {
            val okvirji = anos.okvirji.enakaVrstica(ano.okvir)
            val vsiSoRdeci = okvirji.filter { img.povprecenPiksel(okvir = it).is_red() }.size == okvirji.size
            if (vsiSoRdeci) naslovi.addAll(okvirji)
        }
        stran.naslov.addAll(naslovi)
    }

    fun dodaj_anotacije_podnalog(stran: Stran, anos: List<Anotacija>) {
        val crkaOklepaj = najdi_kandidate_podnalog(anos).toSet()
        val matrika = crkaOklepaj.matrika

        for (kandidati in matrika) {
            val vrstica = mutableListOf(kandidati.first())
            for (x in 1 until kandidati.size) {
                val prva = this.slKoda(vrstica.last().prvaCrka)
                val druga = this.slKoda(kandidati[x].prvaCrka)
                if (prva - druga == -1) vrstica.add(kandidati[x])
            }
            stran.podnaloge.addAll(vrstica.toSet().okvirji)
        }
    }

    private fun najdi_kandidate_podnalog(anotacije: List<Anotacija>): MutableList<Anotacija> {
        val oklepaji = anotacije.filter { it.text == ")" }

        val returned = mutableListOf<Anotacija>()
        for (oklepaj in oklepaji) {
            val vrstica = anotacije.toSet().enakaVrstica(ano = oklepaj)
            val i = vrstica.indexOf(oklepaj)

            val prejsnji = vrstica.getOrNull(i - 1)
            val naslednji = vrstica.getOrNull(i + 1)
            val predprejsnji = vrstica.getOrNull(i - 2)

            val levaOddaljenost = predprejsnji?.okvir?.let { prejsnji?.okvir?.oddaljenost(it) } ?: 1e3
            val desnaOddaljenost = oklepaj.okvir.let { naslednji?.okvir?.oddaljenost(it) } ?: 1e3

            if (
                prejsnji != null
                && prejsnji.text.length == 1
                && this.slKoda(prejsnji.prvaCrka) > -1
                && levaOddaljenost > 40
                && desnaOddaljenost > 35
            ) returned.add(prejsnji)
        }
        return returned
    }

    private fun slKoda(crka: Char): Int {
        var c = crka
        if (c == '1') c = 'l' //Google prepozna l kot 1 :(
        return "abcdefghijklmnoprstuvz".indexOf(c)
    }

}
