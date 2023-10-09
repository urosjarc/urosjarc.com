package data.domain

import kotlinx.serialization.Serializable

@Serializable
data class Annotation(
    val x: Int,
    val y: Int,
    val height: Int,
    val width: Int,
    val text: String,
    var tip: Tip,
) {
    enum class Tip { FOOTER, NALOGA, NASLOV, HEAD, NEZNANO, TEORIJA }

    val x_max: Int get() = this.x + this.width

    val y_max: Int get() = this.y + this.height

    val area: Int get() = this.width * this.height

    val average: Vector<Int> get() = Vector(x = this.x + this.width / 2, y = this.y + this.height / 2)

    fun contains(x: Int, y: Int): Boolean {
        return x in this.x..this.x_max && y in this.y..this.y_max
    }


}
