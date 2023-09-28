package data.extend

import com.google.cloud.vision.v1.EntityAnnotation
import data.domain.MinMax
import data.domain.Vector

fun EntityAnnotation.xy(): Vector<MutableList<Int>> {
    val xs = mutableListOf<Int>()
    val ys = mutableListOf<Int>()

    for (vertex in this.boundingPoly.verticesList) {
        xs.add(vertex.x)
        ys.add(vertex.y)
    }

    return Vector(x = xs, y = ys)
}

fun EntityAnnotation.xMin(): Int {
    val pair = this.xy()
    return pair.x.min()
}

fun EntityAnnotation.xMax(): Int {
    val pair = this.xy()
    return pair.x.max()
}

fun EntityAnnotation.yMin(): Int {
    val pair = this.xy()
    return pair.y.min()
}

fun EntityAnnotation.yMax(): Int {
    val pair = this.xy()
    return pair.y.max()
}


fun EntityAnnotation.xMinMax(): MinMax<Int> {
    val pair = this.xy()
    return MinMax(min = pair.x.min(), max = pair.x.max())
}

fun EntityAnnotation.yMinMax(): MinMax<Int> {
    val pair = this.xy()
    return MinMax(min = pair.y.min(), max = pair.y.max())
}

fun EntityAnnotation.area(): Int {
    val pair = this.xy()
    val dx = pair.x.max() - pair.x.min()
    val dy = pair.y.max() - pair.y.min()
    return dx * dy
}


fun EntityAnnotation.average(): Vector<Int> {
    val pair = this.xy()
    return Vector(x = pair.x.average().toInt(), y = pair.y.average().toInt())
}
