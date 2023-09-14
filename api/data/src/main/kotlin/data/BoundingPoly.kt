package data

import com.google.cloud.vision.v1.BoundingPoly

fun BoundingPoly.xy(): Pair<MutableList<Int>, MutableList<Int>> {
    val xs = mutableListOf<Int>()
    val ys = mutableListOf<Int>()

    for (vertex in this.verticesList) {
        xs.add(vertex.x)
        ys.add(vertex.y)
    }

    return Pair(xs, ys)
}

fun BoundingPoly.xMinMax(): Pair<Int, Int> {
    val pair = this.xy()
    return Pair(pair.first.min(), pair.first.max())
}

fun BoundingPoly.yMinMax(): Pair<Int, Int> {
    val pair = this.xy()
    return Pair(pair.second.min(), pair.second.max())
}

fun BoundingPoly.average(): Pair<Int, Int> {
    val pair = this.xy()
    return Pair(pair.first.average().toInt(), pair.second.average().toInt())
}
