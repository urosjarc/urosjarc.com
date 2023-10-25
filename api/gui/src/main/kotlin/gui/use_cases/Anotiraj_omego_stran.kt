package gui.use_cases

import gui.domain.Anotacija
import gui.domain.Okvir
import gui.domain.Stran
import gui.extend.*
import java.awt.image.BufferedImage


class Anotiraj_omego_stran {

    fun zdaj(img: BufferedImage, anotacije: List<Anotacija>): Stran {
        val stran = Stran(okvir = img.okvir, anotacije = anotacije)
        this.parse_footer(stran = stran, anos = anotacije)
        this.parse_naloge(img = img, stran = stran, anos = anotacije)
        this.parse_naslov(img = img, stran = stran, anos = anotacije)
        this.parse_head(stran = stran, anos = anotacije)
        this.parse_teorija(img = img, stran = stran, anos = anotacije)
        return stran
    }


    fun parse_teorija(img: BufferedImage, stran: Stran, anos: List<Anotacija>) {
        if (stran.naslov.isNotEmpty()) {
            val zgornja_meja = stran.naslov.najnizjaMeja(default = img.height)
            val najnizja_spodnja_meja = stran.noga.najvisjaMeja(default = img.height)
            val spodnja_meja = stran.naloge.najblizjaSpodnjaMeja(meja = zgornja_meja, default = najnizja_spodnja_meja)
            stran.teorija.addAll(anos.okvirji.medY(zgornja_meja = zgornja_meja, spodnja_meja = spodnja_meja))
        }
    }

    fun parse_footer(stran: Stran, anos: List<Anotacija>) {
        val najnizji = anos.okvirji.najnizji
        if (najnizji != null) {
            val y = najnizji.povprecje.y
            val dy = najnizji.visina / 2
            stran.noga.addAll(anos.okvirji.medY(zgornja_meja = y - dy, spodnja_meja = y + dy))
        }
    }

    fun parse_naloge(img: BufferedImage, stran: Stran, anos: List<Anotacija>) {
        for (anno in anos) {
            val pass = img.povprecenPiksel(anno.okvir).is_red()
            val hasEndDot = anno.text.endsWith(".")
            if (hasEndDot && pass) {
                val okvirji = anos.okvirji  //Pridobivanje annotationov ki so rdeci in pripadajo isti vrstici in so levo od pike ter dovolj blizu
                    .enakaVrstica(anno.okvir)
                    .desno(okvir = anno.okvir)
                    .filter { it.end.x - anno.okvir.start.x < 20 }
                    .filter { img.povprecenPiksel(it).is_red() }
                    .sortedBy { it.povprecje.x }.toMutableList()

                okvirji.add(anno.okvir)
                if (okvirji.povrsina in 30 * 30..300 * 50) {
                    stran.naloge.addAll(okvirji)
                }
            }
        }
        if (stran.naloge.isNotEmpty()) stran.naloge.sortBy { it.povprecje.y }
    }

    fun parse_naslov(img: BufferedImage, stran: Stran, anos: List<Anotacija>) {
        /**
         * Parsanje naslovov
         */
        val naslovi = mutableListOf<List<Okvir>>()
        for (anno in anos) {
            val nums = anno.text.split(".").map { it.toIntOrNull() }
            if (!nums.contains(null) && img.povprecenPiksel(anno.okvir).is_red()) {
                val okvirji = anos.okvirji.enakaVrstica(anno.okvir).desno(anno.okvir).sortedBy { it.povprecje.x }
                if (okvirji.povrsina in 150 * 150..1000 * 150) naslovi.add(okvirji)
            }
        }
        if (naslovi.isNotEmpty()) {
            //Najdi naslov z najvecjo povrsino
            stran.naslov = naslovi.maxBy { it.povrsina }.toMutableList()
        }
    }

    fun parse_head(stran: Stran, anos: List<Anotacija>) {
        val najvisji_okvirji = stran.naloge + stran.naslov
        if (najvisji_okvirji.isEmpty()) return
        val spodnja_meja = najvisji_okvirji.najvisjaMeja(default = 0)
        stran.glava = anos.okvirji.nad(meja = spodnja_meja).toMutableList()
    }

}
