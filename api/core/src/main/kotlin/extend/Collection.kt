package extend

fun <T> MutableSet<T>.nakljucni(n: Int): MutableSet<T> {
    return (0 until n).map { this.random() }.toMutableSet()
}

fun <T> MutableSet<T>.dodaj(ele: T): MutableSet<T> {
    this.add(ele)
    return this
}
