package gui.extend

import gui.domain.Anotacija
import gui.domain.Okvir



/**
 * Math
 */

val List<Okvir>.povrsina get() = this.sumOf { it.povrsina }

/**
 * Vzporedne okvirtacije
 */
fun List<Okvir>.enakaVrstica(okvir: Okvir): List<Okvir> = this.filter { okvir.enakaVrstica(it) }
fun List<Okvir>.enakStolpec(okvir: Okvir): List<Okvir> = this.filter { okvir.enakStolpec(it) }
fun List<Okvir>.vOkvirju(okvir: Okvir): List<Okvir> = this.filter { okvir.vsebuje(it) }

fun List<Okvir>.medY(zgornja_meja: Int, spodnja_meja: Int): List<Okvir> =
    this.filter { it.povprecje.y in zgornja_meja..spodnja_meja }

fun List<Okvir>.medX(leva_meja: Int, desna_meja: Int): List<Okvir> =
    this.filter { it.povprecje.x in leva_meja..desna_meja }

/**
 * Dobi okvirtacije glede na vse smeri od meje ali okvirtacije
 */

fun List<Okvir>.nad(meja: Int): List<Okvir> = this.filter { it.end.y < meja }
fun List<Okvir>.nad(okvir: Okvir): List<Okvir> = this.nad(meja = okvir.start.y)
fun List<Okvir>.pod(meja: Int): List<Okvir> = this.filter { it.start.y > meja }
fun List<Okvir>.pod(okvir: Okvir): List<Okvir> = this.pod(meja = okvir.end.y)
fun List<Okvir>.desno(meja: Int): List<Okvir> = this.filter { it.start.x > meja }
fun List<Okvir>.desno(okvir: Okvir): List<Okvir> = this.desno(meja = okvir.end.x)
fun List<Okvir>.levo(meja: Int): List<Okvir> = this.filter { it.end.x < meja }
fun List<Okvir>.levo(okvir: Okvir): List<Okvir> = this.levo(meja = okvir.start.x)

/**
 * Dobi extremne okvirtacije /meje
 */
val List<Okvir>.najnizji get(): Okvir? = this.maxByOrNull { it.end.y }
val List<Okvir>.najvisji get(): Okvir? = this.minByOrNull { it.start.y }
fun List<Okvir>.levaMeja(default: Int): Int = this.minOfOrNull { it.start.x } ?: default
fun List<Okvir>.desnaMeja(default: Int): Int = this.maxOfOrNull { it.end.x } ?: default
fun List<Okvir>?.najnizjaMeja(default: Int): Int = this?.najnizji?.end?.y ?: default
fun List<Okvir>?.najvisjaMeja(default: Int): Int = this?.najvisji?.start?.y ?: default

/**
 * Dobi najblizje okvirtacije glede na mejo
 */
fun List<Okvir>.najblizjiSpodaj(meja: Int): Okvir? = this.pod(meja = meja).minByOrNull { it.start.y }
fun List<Okvir>.najblizjiZgoraj(meja: Int): Okvir? = this.nad(meja = meja).maxByOrNull { it.end.y }
fun List<Okvir>.najblizjiLevo(meja: Int): Okvir? = this.levo(meja = meja).maxByOrNull { it.end.x }
fun List<Okvir>.najblizjiDesno(meja: Int): Okvir? = this.desno(meja = meja).maxByOrNull { it.start.x }


/**
 * Dobi najblizje meje glede na mejo
 */
fun List<Okvir>?.najblizjaSpodnjaMeja(meja: Int, default: Int): Int = this?.najblizjiSpodaj(meja = meja)?.start?.y ?: default
fun List<Okvir>?.najblizjaZgornjaMeja(meja: Int, default: Int): Int = this?.najblizjiZgoraj(meja = meja)?.end?.y ?: default
fun List<Okvir>?.najblizjaLevaMeja(meja: Int, default: Int): Int = this?.najblizjiLevo(meja = meja)?.end?.x ?: default
fun List<Okvir>?.najblizjaDesnaMeja(meja: Int, default: Int): Int = this?.najblizjiDesno(meja = meja)?.start?.x ?: default

/**
 * Dobi najblizjo okvirtacije glede na okvirtacijo
 */
fun List<Okvir>.najblizjiSpodaj(okvir: Okvir): Okvir? = this.pod(meja = okvir.end.y).minByOrNull { it.start.y }
fun List<Okvir>.najblizjiZgoraj(okvir: Okvir): Okvir? = this.nad(meja = okvir.start.y).maxByOrNull { it.end.y }
fun List<Okvir>.najblizjiLevo(okvir: Okvir): Okvir? = this.levo(meja = okvir.start.x).maxByOrNull { it.end.x }
fun List<Okvir>.najblizjiDesno(okvir: Okvir): Okvir? = this.desno(meja = okvir.end.x).minByOrNull { it.start.x }

/**
 * Dobi najblizje meje glede na okvirtacijo
 */
fun List<Okvir>?.najblizjaSpodnjaMeja(okvir: Okvir, default: Int): Int = this?.najblizjiSpodaj(okvir = okvir)?.end?.y ?: default
fun List<Okvir>?.najblizjaZgornjaMeja(okvir: Okvir, default: Int): Int = this?.najblizjiZgoraj(okvir = okvir)?.start?.y ?: default
fun List<Okvir>?.najblizjaLevaMeja(okvir: Okvir, default: Int): Int = this?.najblizjiLevo(okvir = okvir)?.end?.x ?: default
fun List<Okvir>?.najblizjaDesnaMeja(okvir: Okvir, default: Int): Int = this?.najblizjiDesno(okvir = okvir)?.start?.x ?: default
