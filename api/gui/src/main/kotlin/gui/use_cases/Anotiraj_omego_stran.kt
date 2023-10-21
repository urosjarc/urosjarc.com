package gui.use_cases

import gui.domain.Anotacija
import gui.domain.Slika
import gui.domain.Stran
import gui.extend.*

class Anotiraj_omego_stran {

    fun zdaj(slika: Slika, anos: List<Anotacija>): Stran {
        val stran = Stran(slika = slika, anotacije = anos)

        this.parse_footer(stran, anos)
        this.parse_naloge(stran, anos)
        this.parse_naslov(stran, anos)
        this.parse_head(stran, anos)
        this.parse_teorija(stran, anos)

        stran.init()
        return stran
    }


    fun parse_teorija(stran: Stran, anos: List<Anotacija>) {
        if (stran.naslov.isNotEmpty()) {
            val zgornja_meja = stran.naslov.najnizjaMeja(default = stran.visina)
            val najnizja_spodnja_meja = stran.noga.najvisjaMeja(default = stran.visina)
            val spodnja_meja = stran.naloge.map { it.first() }.najblizjaSpodnjaMeja(meja = zgornja_meja, default = najnizja_spodnja_meja)
            stran.teorija.addAll(anos.medY(zgornja_meja = zgornja_meja, spodnja_meja = spodnja_meja))
        }
    }

    fun parse_footer(stran: Stran, anos: List<Anotacija>) {
        val najnizji = anos.najnizji()
        if (najnizji != null) {
            val y = najnizji.average.y
            val dy = najnizji.height / 2
            stran.noga.addAll(anos.medY(zgornja_meja = y - dy, spodnja_meja = y + dy))
        }
    }

    fun parse_naloge(stran: Stran, anos: List<Anotacija>) {
        val img = stran.slika.img
        for (anno in anos) {
            val pass = img.povprecenPiksel(anno).is_red()
            val hasEndDot = anno.text.endsWith(".")
            if (hasEndDot && pass) {
                val nalogeAnnos = anos  //Pridobivanje annotationov ki so rdeci in pripadajo isti vrstici in so levo od pike ter dovolj blizu
                    .vzporedne(anno)
                    .desno(ano = anno)
                    .filter { it.x_max - anno.x < 20 }
                    .filter { img.povprecenPiksel(it).is_red() }
                    .sortedBy { it.average.x }.toMutableList()

                nalogeAnnos.add(anno)
                if (nalogeAnnos.povrsina().vmes(30 * 30, 300 * 50)) {
                    stran.naloge.add(nalogeAnnos)
                }
            }
        }
        if (stran.naloge.isNotEmpty()) stran.naloge.sortBy { it.first().average.y }
    }

    fun parse_naslov(stran: Stran, anos: List<Anotacija>) {
        /**
         * Parsanje naslovov
         */
        val naslovi = mutableListOf<List<Anotacija>>()
        for (anno in anos) {
            val nums = anno.text.split(".").map { it.toIntOrNull() }
            if (!nums.contains(null) && stran.slika.img.povprecenPiksel(anno).is_red()) {
                val deli = anos.vzporedne(anno).desno(anno).sortedBy { it.average.x }
                if (deli.povrsina().vmes(150 * 150, 1000 * 150)) naslovi.add(deli)
            }
        }
        if (naslovi.isNotEmpty()) {
            //Najdi naslov z najvecjo povrsino
            stran.naslov = naslovi.maxBy { it.povrsina() }.toMutableList()
        }
    }

    fun parse_head(stran: Stran, anos: List<Anotacija>) {
        val najvisje_anotacije = stran.naloge.map { it.first() } + stran.naslov
        if (najvisje_anotacije.isEmpty()) return
        val spodnja_meja = najvisje_anotacije.najvisjaMeja(default = 0.0)
        stran.glava = anos.nad(meja = spodnja_meja).toMutableList()
    }

}
