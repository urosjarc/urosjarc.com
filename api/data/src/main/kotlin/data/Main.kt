package data

import data.app.ZipPart
import data.app.init_tessaract
import data.app.tesseract
import data.books.omega_zip_iterator
import java.nio.file.Paths
import kotlin.io.path.Path

fun main() {
    init_tessaract()

    val resources = Path("src/main/resources")
    val iterator = omega_zip_iterator(skip = 7, file = Paths.get(resources.toString(), "Omega11.zip").toFile())
    val saveDir = Paths.get(resources.toString(), "Omega11")
    val teorija = Paths.get(saveDir.toString(), "Teorija0")

    saveDir.toFile().mkdir()
    for ((si, slika) in iterator.withIndex()) {
        for ((pi, part) in slika.parts.withIndex()) {
            val text = tesseract.doOCR(part.image)
            when (part.tip) {
                ZipPart.Tip.naloga -> TODO()
                ZipPart.Tip.naslov -> TODO()
                ZipPart.Tip.teorija -> {
                }
                ZipPart.Tip.prazno -> TODO()
            }
        }
    }
}
