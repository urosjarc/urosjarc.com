package data.extend

fun <T : Any> MutableMap<T, Int>.increment(key: T) {
    this.merge(key, 1, Int::plus)
}

fun <T : Any> MutableMap<T, Int>.reset(key: T) {
    this[key] = 0
}

fun <T : Any> MutableMap<T, Int>.resetAll() {
    for (key in this.keys) {
        this[key] = 0
    }
}
