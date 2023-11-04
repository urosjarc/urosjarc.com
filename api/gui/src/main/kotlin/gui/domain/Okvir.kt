package gui.domain

import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import kotlinx.serialization.Serializable

@Serializable
data class Okvir(
    var start: Vektor,
    var end: Vektor
) {
    companion object {
        val PRAZEN get() = Okvir(start = Vektor.NIC, end = Vektor.NIC)
    }

    val sirina: Int get() = this.end.x - this.start.x
    val visina: Int get() = this.end.y - this.start.y
    val povrsina: Int get() = this.sirina * this.visina
    val povprecje: Vektor get() = Vektor(x = this.start.x + this.sirina / 2, y = this.start.y + this.visina / 2)
    fun enakaVrstica(okvir: Okvir): Boolean = okvir.povprecje.y in this.start.y..this.end.y
    fun enakStolpec(okvir: Okvir): Boolean = okvir.povprecje.x in this.start.x..this.end.x
    fun vsebuje(okvir: Okvir): Boolean = this.enakStolpec(okvir) && this.enakaVrstica(okvir)
    fun vsebujeRob(okvir: Okvir): Boolean =
        (okvir.start.y in this.start.y + 1..this.end.y - 1 || okvir.end.y in this.start.y + 1..this.end.y - 1) &&
                (okvir.start.x in this.start.x + 1..this.end.x - 1 || okvir.end.x in this.start.x + 1..this.end.x - 1)

    fun vsebuje(vektor: Vektor): Boolean = vektor.x in this.start.x..this.end.x && vektor.y in this.start.y..this.end.y
    fun vRectangle(color: Color = Color.BLACK, round: Int = 0, width: Double = 2.0): Rectangle {
        return Rectangle(
            this.start.x.toDouble() - width,
            this.start.y.toDouble() - width,
            this.sirina.toDouble() + 2 * width,
            this.visina.toDouble() + 2 * width
        ).apply {
            this.fill = null
            this.stroke = color
            this.arcHeight = round.toDouble()
            this.arcWidth = round.toDouble()
            this.strokeWidth = width
        }
    }
}
