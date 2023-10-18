package gui.domain

import kotlinx.serialization.Serializable

@Serializable
data class Anotacija(
    val x: Double,
    val y: Double,
    val height: Double,
    val width: Double,
    val text: String,
    var tip: Tip,
) {
    enum class Tip {
        NEZNANO,
        HEAD,
        NASLOV,
        TEORIJA,
        NALOGA,
        FOOTER,
    }

    val x_max: Double get() = this.x + this.width

    val y_max: Double get() = this.y + this.height

    val area: Double get() = this.width * this.height

    val average: Vektor get() = Vektor(x = this.x + this.width / 2.0, y = this.y + this.height / 2.0)

    fun contains(x: Double, y: Double): Boolean {
        return x in this.x..this.x_max && y in this.y..this.y_max
    }


}
