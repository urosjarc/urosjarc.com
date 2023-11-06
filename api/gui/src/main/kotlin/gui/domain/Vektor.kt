package gui.domain

import kotlinx.serialization.Serializable
import kotlin.math.pow

@Serializable
data class Vektor(
    var x: Int,
    var y: Int,
) {
    companion object {
        val NIC get() = Vektor(x = 0, y = 0)
    }

    fun oddaljenost(vektor: Vektor): Double {
        val dx = (this.x - vektor.x).toDouble().pow(2)
        val dy = (this.y - vektor.y).toDouble().pow(2)
        return (dx + dy).pow(0.5)
    }
}
