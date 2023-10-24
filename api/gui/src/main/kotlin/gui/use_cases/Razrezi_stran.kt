package gui.use_cases

import gui.domain.Odsek
import gui.domain.Stran
import gui.domain.Vektor
import gui.extend.*
import kotlin.math.abs

class Razrezi_stran {

    fun zdaj(stran: Stran): MutableList<Odsek> {
        val deli = mutableListOf<Odsek>()
        /**
         * Glava
         */
        if (stran.glava.size > 0) {
            val spodnja_meja = stran.glava.najnizjaMeja(default = stran.visina)
            val najnizja_spodnja_meja = stran.anotacije.najblizjaSpodnjaMeja(meja = spodnja_meja, default = spodnja_meja)
            val subImg = stran.slika.img.getSubimage(0, 0, stran.sirina.toInt(), najnizja_spodnja_meja.toInt())
            val border = subImg.odstraniObrobo(50)
            val slika = stran.slika.copy(img = border.second)
            val vektor = Vektor(x = border.first.toDouble(), y = (0 + border.first).toDouble())
            deli.add(Odsek(pozicija = vektor, slika = slika, anotacije = stran.glava, tip = Odsek.Tip.GLAVA))
        }

        /**
         * Teorija
         */
        if (stran.teorija.size > 0) {
            val zgornja_meja = stran.teorija.najvisjaMeja(default = 0.0)
            val spodnja_meja = stran.teorija.najnizjaMeja(default = stran.visina)
            val najvisja_zgornja_meja = stran.anotacije.najblizjaZgornjaMeja(meja = zgornja_meja, default = zgornja_meja).toInt()
            val najnizja_spodnja_meja = stran.anotacije.najblizjaSpodnjaMeja(meja = spodnja_meja, default = spodnja_meja).toInt()
            val subImg = stran.slika.img.getSubimage(0, najvisja_zgornja_meja, stran.sirina.toInt(), najnizja_spodnja_meja)
            val slika = stran.slika.copy(img = subImg)
            val vektor = Vektor(x = 0.0, y = najvisja_zgornja_meja.toDouble())
            deli.add(Odsek(pozicija = vektor, slika = slika, anotacije = stran.teorija, tip = Odsek.Tip.TEORIJA))
        }

        /**
         * Find footer
         */
        val nalogaY = stran.naloge.map { it.first().y }.toMutableList()
        nalogaY.add(stran.noga[0].y)

        /**
         * Naloge
         */
        for (i in 0 until nalogaY.size - 1) {
            val zgornja_meja = stran.anotacije.najblizjaZgornjaMeja(meja = nalogaY[i], default = 0.0)
            val spodnja_meja = nalogaY[i + 1]
            val visina = abs(spodnja_meja - zgornja_meja)
            val subImg = stran.slika.img.getSubimage(0, zgornja_meja.toInt(), stran.sirina.toInt(), visina.toInt())
            val anos = stran.anotacije.medY(zgornja_meja = zgornja_meja, spodnja_meja = spodnja_meja)
            val border = subImg.odstraniObrobo(50)
            val slika = stran.slika.copy(img = border.second)
            deli.add(Odsek(x = border.first, y = zgornja_meja.toInt() + border.first, slika = slika, anotacije = anos, tip = Odsek.Tip.NALOGA))
        }

        /**
         * Ustvari kopije za vsak primer da ne bi bilo kaj narobe.
         */
        deli.sortBy { it.anotacije.first().y }
        deli.forEach { odsek ->
            odsek.anotacije = odsek.anotacije.map {
                it.copy(
                    y = it.y - odsek.y,
                    x = it.x - odsek.x
                )
            }
        }

        return deli
    }
}
