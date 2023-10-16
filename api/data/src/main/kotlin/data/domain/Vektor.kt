package data.domain

import kotlin.math.abs
import kotlin.math.min

data class Vektor(
    val x: Double,
    val y: Double,
) {
    fun okvir(v: Vektor): Okvir {
        val minX = min(v.x, this.x)
        val minY = min(v.y, this.y)
        val width = abs(v.x - this.x)
        val height = abs(v.y - this.y)
        return Okvir(
            x0 = minX,
            x1 = minX + width,
            y0 = minY,
            y1 = minY + height
        )
    }
}
