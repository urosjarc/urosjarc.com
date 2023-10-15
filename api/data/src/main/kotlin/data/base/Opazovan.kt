package data.base

class Opazovan<T>(private var _vrednost: T) {
    var value: T
        get() = this._vrednost
        set(v) {
            this._vrednost = v
            this.opazovalci.forEach {
                it(this._vrednost)

            }
        }

    val opazovalci = mutableListOf<(value: T) -> Unit>()
    fun opazuj(cb: ((value: T) -> Unit)) {
        this.opazovalci.add(cb)
    }

}
