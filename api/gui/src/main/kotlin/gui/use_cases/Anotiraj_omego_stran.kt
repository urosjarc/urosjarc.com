package gui.use_cases

import gui.domain.Anotacija
import gui.domain.Okvir
import gui.domain.Stran
import gui.extend.*
import java.awt.image.BufferedImage


class Anotiraj_omego_stran {

    fun zdaj(img: BufferedImage, anotacije: List<Anotacija>): Stran {
        val stran = Stran(okvir = img.okvir, anotacije = anotacije.toSet())

        this.parse_footer(stran = stran, anos = anotacije.toSet())
        this.parse_naloge(img = img, stran = stran, anos = anotacije.toSet())
        this.parse_podnaloge(stran = stran, anos = anotacije)
        this.parse_naslov(img = img, stran = stran, anos = anotacije.toSet())
        this.parse_teorija(img = img, stran = stran, anos = anotacije.toSet())

        return stran
    }


    fun parse_teorija(img: BufferedImage, stran: Stran, anos: Set<Anotacija>) {
        if (stran.naslov.isNotEmpty()) {
            val zgornja_meja = stran.naslov.najnizjaMeja(default = img.height)
            val najnizja_spodnja_meja = stran.noga.najvisjaMeja(default = img.height)
            val spodnja_meja = stran.naloge.najblizjaSpodnjaMeja(meja = zgornja_meja, default = najnizja_spodnja_meja)
            val okvirji = anos.okvirji.medY(zgornja_meja = zgornja_meja, spodnja_meja = spodnja_meja)
            stran.teorija.addAll(okvirji)
        }
    }

    fun parse_footer(stran: Stran, anos: Set<Anotacija>) {
        val najnizji = anos.okvirji.najnizji
        if (najnizji != null) {
            val y = najnizji.povprecje.y
            val dy = najnizji.visina / 2
            stran.noga.addAll(anos.okvirji.medY(zgornja_meja = y - dy, spodnja_meja = y + dy))
        }
    }

    fun parse_naloge(img: BufferedImage, stran: Stran, anos: Set<Anotacija>) {
        for (ano in anos) {
            val isRed = img.povprecenPiksel(ano.okvir).is_red()
            val isInt = ano.text.removeSuffix(".").toIntOrNull() != null
            val isFloat = ano.text.toFloatOrNull() != null
            val hasEndDot = ano.text.endsWith('.')
            if (isRed && (isInt || (isFloat && hasEndDot))) {
                val okvirji = anos.okvirji  //Pridobivanje annotationov ki so rdeci in pripadajo isti vrstici in so levo od pike ter dovolj blizu
                    .enakaVrstica(ano.okvir)
                    .desno(okvir = ano.okvir)
                    .filter { it.end.x - ano.okvir.start.x < 20 }
                    .filter { img.povprecenPiksel(it).is_red() }
                    .sortedBy { it.povprecje.x }.toMutableSet()

                okvirji.add(ano.okvir)
                if (okvirji.povrsina in 30 * 30..300 * 50) {
                    stran.naloge.addAll(okvirji)
                }
            }
        }
        if (stran.naloge.isNotEmpty()) stran.naloge
    }

    fun parse_naslov(img: BufferedImage, stran: Stran, anos: Set<Anotacija>) {
        /**
         * Parsanje naslovov
         */
        val naslovi = mutableSetOf<Set<Okvir>>()
        for (ano in anos) {
            val isRed = img.povprecenPiksel(ano.okvir).is_red()
            val isFloat = ano.text.toFloatOrNull() != null
            val hasMiddleDot = ano.text.indexOf('.') in 1..ano.text.length - 2
            val isLong = ano.text.length >= 3
            if (isRed && isLong && isFloat && hasMiddleDot) {
                val okvirji = anos.okvirji.enakaVrstica(ano.okvir).desno(ano.okvir).sortedBy { it.povprecje.x }.toSet()
                if (okvirji.povrsina in 150 * 50..1000 * 150) naslovi.add(okvirji)
            }
        }
        if (naslovi.isNotEmpty()) {
            //Najdi naslov z najvecjo povrsino
            stran.naslov = naslovi.maxBy { it.povrsina }.toMutableSet()
        }
    }

    fun parse_podnaloge(stran: Stran, anos: List<Anotacija>) {
        val crkaOklepaj = vseCrkeZOklepajem(anos)
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
            stran.podnaloge.addAll(grupa.toSet().okvirji)
        }
    }

    private fun vseCrkeZOklepajem(anotacije: List<Anotacija>): MutableList<Anotacija> {
        val oklepaji = anotacije.filter { it.text == ")" }

        val returned = mutableListOf<Anotacija>()
        for (oklepaj in oklepaji) {
            val i = anotacije.indexOf(oklepaj)
            val prejsnji = anotacije[i - 1]
            if (prejsnji.text.length == 1 && this.slKoda(prejsnji.prvaCrka) > -1) returned.add(prejsnji)
        }
        return returned
    }

    private fun slKoda(crka: Char): Int {
        var c = crka
        if (c == '1') c = 'l' //Google prepozna l kot 1 :(
        return "abcdefghijklmnoprstuvz".indexOf(c)
    }

}
