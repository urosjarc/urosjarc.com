package gui.extend

import gui.domain.Anotacija

/**
 * Math
 */

fun List<Anotacija>.povrsina(): Double = this.sumOf { it.area }

/**
 * Vzporedne anotacije
 */
fun List<Anotacija>.vzporedne(ano: Anotacija): List<Anotacija> = this.filter { ano.vzporedna(it) }

fun List<Anotacija>.medY(zgornja_meja: Double, spodnja_meja: Double): List<Anotacija> =
    this.filter { it.average.y.vmes(start = zgornja_meja, end = spodnja_meja) }

/**
 * Dobi anotacije glede na vse smeri od meje ali anotacije
 */

fun List<Anotacija>.nad(meja: Double): List<Anotacija> = this.filter { it.y_max < meja }
fun List<Anotacija>.nad(ano: Anotacija): List<Anotacija> = this.nad(meja = ano.y)
fun List<Anotacija>.pod(meja: Double): List<Anotacija> = this.filter { it.y > meja }
fun List<Anotacija>.pod(ano: Anotacija): List<Anotacija> = this.pod(meja = ano.y_max)
fun List<Anotacija>.desno(meja: Double): List<Anotacija> = this.filter { it.x > meja }
fun List<Anotacija>.desno(ano: Anotacija): List<Anotacija> = this.desno(meja = ano.x_max)
fun List<Anotacija>.levo(meja: Double): List<Anotacija> = this.filter { it.x_max < meja }
fun List<Anotacija>.levo(ano: Anotacija): List<Anotacija> = this.levo(meja = ano.x)

/**
 * Dobi extremne anotacije /meje
 */
fun List<Anotacija>.najnizji(): Anotacija? = this.maxByOrNull { it.y_max }
fun List<Anotacija>.najvisji(): Anotacija? = this.minByOrNull { it.y }
fun List<Anotacija>?.najnizjaMeja(default: Double): Double = this?.najnizji()?.y_max ?: default
fun List<Anotacija>?.najvisjaMeja(default: Double): Double = this?.najvisji()?.y ?: default

/**
 * Dobi najblizje anotacije glede na mejo
 */
fun List<Anotacija>.najblizjiSpodaj(meja: Double): Anotacija? = this.pod(meja = meja).minByOrNull { it.y }
fun List<Anotacija>.najblizjiZgoraj(meja: Double): Anotacija? = this.nad(meja = meja).maxByOrNull { it.y_max }
fun List<Anotacija>.najblizjiLevo(meja: Double): Anotacija? = this.levo(meja = meja).maxByOrNull { it.x_max }
fun List<Anotacija>.najblizjiDesno(meja: Double): Anotacija? = this.desno(meja = meja).maxByOrNull { it.x }


/**
 * Dobi najblizje meje glede na mejo
 */
fun List<Anotacija>?.najblizjaSpodnjaMeja(meja: Double, default: Double): Double = this?.najblizjiSpodaj(meja = meja)?.y ?: default
fun List<Anotacija>?.najblizjaZgornjaMeja(meja: Double, default: Double): Double = this?.najblizjiZgoraj(meja = meja)?.y_max ?: default
fun List<Anotacija>?.najblizjaLevaMeja(meja: Double, default: Double): Double = this?.najblizjiLevo(meja = meja)?.x_max ?: default
fun List<Anotacija>?.najblizjaDesnaMeja(meja: Double, default: Double): Double = this?.najblizjiDesno(meja = meja)?.x ?: default

/**
 * Dobi najblizjo anotacije glede na anotacijo
 */
fun List<Anotacija>.najblizjiSpodaj(ano: Anotacija): Anotacija? = this.pod(meja = ano.y_max).minByOrNull { it.y }
fun List<Anotacija>.najblizjiZgoraj(ano: Anotacija): Anotacija? = this.nad(meja = ano.y).maxByOrNull { it.y_max }
fun List<Anotacija>.najblizjiLevo(ano: Anotacija): Anotacija? = this.levo(meja = ano.x).maxByOrNull { it.x_max }
fun List<Anotacija>.najblizjiDesno(ano: Anotacija): Anotacija? = this.desno(meja = ano.x_max).minByOrNull { it.x }

/**
 * Dobi najblizje meje glede na anotacijo
 */
fun List<Anotacija>?.najblizjaSpodnjaMeja(ano: Anotacija, default: Double): Double = this?.najblizjiSpodaj(ano = ano)?.y_max ?: default
fun List<Anotacija>?.najblizjaZgornjaMeja(ano: Anotacija, default: Double): Double = this?.najblizjiZgoraj(ano = ano)?.y ?: default
fun List<Anotacija>?.najblizjaLevaMeja(ano: Anotacija, default: Double): Double = this?.najblizjiLevo(ano = ano)?.x_max ?: default
fun List<Anotacija>?.najblizjaDesnaMeja(ano: Anotacija, default: Double): Double = this?.najblizjiDesno(ano = ano)?.x ?: default
