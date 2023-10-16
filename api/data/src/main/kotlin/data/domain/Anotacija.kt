package data.domain

import kotlinx.serialization.Serializable

@Serializable
data class Anotacija(
    val x: Int,
    val y: Int,
    val height: Int,
    val width: Int,
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

    val x_max: Int get() = this.x + this.width

    val y_max: Int get() = this.y + this.height

    val area: Int get() = this.width * this.height

    val average: Vektor get() = Vektor(x = this.x + this.width / 2.0, y = this.y + this.height / 2.0)

    fun contains(x: Int, y: Int): Boolean {
        return x in this.x..this.x_max && y in this.y..this.y_max
    }


}
