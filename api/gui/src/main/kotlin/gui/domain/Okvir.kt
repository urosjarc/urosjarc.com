package gui.domain

import kotlinx.serialization.Serializable

@Serializable
data class Okvir(
    val start: Vektor,
    val end: Vektor
) {
    val sirina: Int get() = this.end.x - this.start.x
    val visina: Int get() = this.end.y - this.start.y
    val povrsina: Int get() = this.sirina * this.visina
    val povprecje: Vektor get() = Vektor(x = this.start.x + this.sirina / 2, y = this.start.y + this.visina / 2)
    fun enakaVrstica(okvir: Okvir): Boolean = okvir.povprecje.y in this.start.y..this.end.y
    fun enakStolpec(okvir: Okvir): Boolean = okvir.povprecje.x in this.start.x..this.end.x
    fun vsebuje(okvir: Okvir): Boolean = this.enakStolpec(okvir) && this.enakaVrstica(okvir)
}
