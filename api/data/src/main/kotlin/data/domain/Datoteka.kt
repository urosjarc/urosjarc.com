package data.domain

import java.io.File

class Datoteka(
    val file: File,
) {
    val ime: String get() = this.file.name
}
