package gui.base

class Izbire<T>(var izbire: List<T> = listOf()) {
    var index: Int = 0
    val trenutni: T get() = this.izbire[this.index]

    val aktivni get() = this.izbire.isNotEmpty()

    fun naprej() {
        this.index++
        if (this.index >= this.izbire.size) {
            this.index = 0
        }
    }

    fun nazaj() {
        this.index--
        if (this.index < 0) {
            this.index = this.izbire.size
        }
    }

    fun resetiraj(naKonec: Boolean) {
        this.index = if (naKonec) this.izbire.size - 1 else 0
    }

}
