package gui.domain

import java.io.File

class Datoteka(val file: File) {
    val ime: String get() = this.file.name

    override fun toString(): String {
        return this.ime
    }
}
