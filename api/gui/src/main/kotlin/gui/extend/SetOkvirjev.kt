package gui.extend

import gui.domain.Okvir
import gui.domain.Vektor


/**
 * Math
 */

val Set<Okvir>.najmanjsiOkvir: Okvir
    get() {
        val okvir = Okvir(
            start = Vektor(
                x = this.levaMeja(0),
                y = this.najvisjaMeja(0),
            ),
            end = Vektor(
                x = this.desnaMeja(0),
                y = this.najnizjaMeja(0),
            )
        )

//        if (okvir.povrsina == 0) throw Throwable("Ni anotacij!")
        return okvir
    }

val Set<Okvir>.povrsina get() = this.sumOf { it.povrsina }

val Set<Okvir>.matrika
    get(): MutableList<List<Okvir>> {
        val mat = mutableListOf<List<Okvir>>()
        var najvisji = this.najvisji ?: return mat

        while (true) {
            val vrstica = this.enakaVrstica(okvir = najvisji).sortedBy { it.povprecje.x }
            mat.add(vrstica)
            najvisji = this.najblizjiSpodaj(okvir = najvisji) ?: break
        }

        return mat
    }

/**
 * Vzporedne okvirtacije
 */
fun Set<Okvir>.vsebujejo(vektor: Vektor): Set<Okvir> = this.filter { it.vsebuje(vektor = vektor) }.toSet()
fun Set<Okvir>.enakaVrstica(okvir: Okvir): Set<Okvir> = this.filter { okvir.enakaVrstica(it) }.toSet()
fun Set<Okvir>.enakStolpec(okvir: Okvir): Set<Okvir> = this.filter { okvir.enakStolpec(it) }.toSet()
fun Set<Okvir>.vOkvirju(okvir: Okvir): Set<Okvir> = this.filter { okvir.vsebuje(it) }.toSet()
fun Set<Okvir>.medY(zgornja_meja: Int, spodnja_meja: Int): Set<Okvir> = this.filter { it.povprecje.y in zgornja_meja..spodnja_meja }.toSet()
fun Set<Okvir>.medX(leva_meja: Int, desna_meja: Int): Set<Okvir> = this.filter { it.povprecje.x in leva_meja..desna_meja }.toSet()

/**
 * Dobi okvirtacije glede na vse smeri od meje ali okvirtacije
 */

fun Set<Okvir>.nad(meja: Int): Set<Okvir> = this.filter { it.end.y < meja }.toSet()
fun Set<Okvir>.nad(okvir: Okvir): Set<Okvir> = this.nad(meja = okvir.start.y)
fun Set<Okvir>.pod(meja: Int): Set<Okvir> = this.filter { it.start.y > meja }.toSet()
fun Set<Okvir>.pod(okvir: Okvir): Set<Okvir> = this.pod(meja = okvir.end.y)
fun Set<Okvir>.desno(meja: Int): Set<Okvir> = this.filter { it.start.x > meja }.toSet()
fun Set<Okvir>.desno(okvir: Okvir): Set<Okvir> = this.desno(meja = okvir.end.x)
fun Set<Okvir>.levo(meja: Int): Set<Okvir> = this.filter { it.end.x < meja }.toSet()
fun Set<Okvir>.levo(okvir: Okvir): Set<Okvir> = this.levo(meja = okvir.start.x)

/**
 * Dobi extremne okvirtacije /meje
 */
val Set<Okvir>.najnizji get(): Okvir? = this.maxByOrNull { it.end.y }
val Set<Okvir>.najvisji get(): Okvir? = this.minByOrNull { it.start.y }
fun Set<Okvir>.levaMeja(default: Int): Int = this.minOfOrNull { it.start.x } ?: default
fun Set<Okvir>.desnaMeja(default: Int): Int = this.maxOfOrNull { it.end.x } ?: default
fun Set<Okvir>?.najnizjaMeja(default: Int): Int = this?.najnizji?.end?.y ?: default
fun Set<Okvir>?.najvisjaMeja(default: Int): Int = this?.najvisji?.start?.y ?: default

/**
 * Dobi najblizje okvirtacije glede na mejo
 */
fun Set<Okvir>.najblizjiSpodaj(meja: Int): Okvir? = this.pod(meja = meja).minByOrNull { it.start.y }
fun Set<Okvir>.najblizjiZgoraj(meja: Int): Okvir? = this.nad(meja = meja).maxByOrNull { it.end.y }
fun Set<Okvir>.najblizjiLevo(meja: Int): Okvir? = this.levo(meja = meja).maxByOrNull { it.end.x }
fun Set<Okvir>.najblizjiDesno(meja: Int): Okvir? = this.desno(meja = meja).maxByOrNull { it.start.x }


/**
 * Dobi najblizje meje glede na mejo
 */
fun Set<Okvir>?.najblizjaSpodnjaMeja(meja: Int, default: Int): Int = this?.najblizjiSpodaj(meja = meja)?.start?.y ?: default
fun Set<Okvir>?.najblizjaZgornjaMeja(meja: Int, default: Int): Int = this?.najblizjiZgoraj(meja = meja)?.end?.y ?: default
fun Set<Okvir>?.najblizjaLevaMeja(meja: Int, default: Int): Int = this?.najblizjiLevo(meja = meja)?.end?.x ?: default
fun Set<Okvir>?.najblizjaDesnaMeja(meja: Int, default: Int): Int = this?.najblizjiDesno(meja = meja)?.start?.x ?: default

/**
 * Dobi najblizjo okvirtacije glede na okvirtacijo
 */
fun Set<Okvir>.najblizjiSpodaj(okvir: Okvir): Okvir? = this.pod(meja = okvir.end.y).minByOrNull { it.start.y }
fun Set<Okvir>.najblizjiZgoraj(okvir: Okvir): Okvir? = this.nad(meja = okvir.start.y).maxByOrNull { it.end.y }
fun Set<Okvir>.najblizjiLevo(okvir: Okvir): Okvir? = this.levo(meja = okvir.start.x).maxByOrNull { it.end.x }
fun Set<Okvir>.najblizjiDesno(okvir: Okvir): Okvir? = this.desno(meja = okvir.end.x).minByOrNull { it.start.x }

/**
 * Dobi najblizje meje glede na okvirtacijo
 */
fun Set<Okvir>?.najblizjaSpodnjaMeja(okvir: Okvir, default: Int): Int = this?.najblizjiSpodaj(okvir = okvir)?.start?.y ?: default
fun Set<Okvir>?.najblizjaZgornjaMeja(okvir: Okvir, default: Int): Int = this?.najblizjiZgoraj(okvir = okvir)?.end?.y ?: default
fun Set<Okvir>?.najblizjaLevaMeja(okvir: Okvir, default: Int): Int = this?.najblizjiLevo(okvir = okvir)?.end?.x ?: default
fun Set<Okvir>?.najblizjaDesnaMeja(okvir: Okvir, default: Int): Int = this?.najblizjiDesno(okvir = okvir)?.start?.x ?: default
