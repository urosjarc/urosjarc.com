package gui.domain

import kotlin.math.abs
import kotlin.math.min

data class Vektor(
    val x: Double,
    val y: Double,
) {
    fun okvir(v: Vektor): Okvir {
        val minX = min(v.x, this.x).toInt()
        val minY = min(v.y, this.y).toInt()
        val width = abs(v.x - this.x).toInt()
        val height = abs(v.y - this.y).toInt()
        return Okvir(
            x0 = minX,
            x1 = minX + width,
            y0 = minY,
            y1 = minY + height
        )
    }
}
