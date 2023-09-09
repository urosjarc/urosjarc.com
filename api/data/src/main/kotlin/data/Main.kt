package data

import data.app.ZipPart
import data.app.init_tessaract
import data.app.tesseract
import data.books.omega_zip_iterator
import data.extend.blackWhite
import data.extend.boundBox
import data.extend.getSafeSubimage
import data.extend.save
import java.io.File

fun main() {
    init_tessaract()

    val resourceFile = File("src/main/resources")
    val saveDir = File(resourceFile, "Omega11")
    var teorijaDir = File(saveDir, "Teorija0")

    saveDir.mkdir()
    teorijaDir.mkdir()
    println("Init teorija dir: $teorijaDir")

    val iterator = omega_zip_iterator(skip = 7, file = File(resourceFile, "Omega11.zip"))
    for ((si, slika) in iterator.withIndex()) {
        for ((pi, part) in slika.parts.withIndex()) {

            val bb = part.image.boundBox(70, 80)

            when (part.tip) {
                ZipPart.Tip.naloga -> {
                    val nalogaFile = File(teorijaDir, "naloga_$pi.png")
                    println("Save naloga: $nalogaFile")
                    part.image = part.image.getSafeSubimage(bb.x0 - 30, bb.y0 - 40, bb.width() + 100, bb.height() + 80)
                    part.image.save(nalogaFile)
                }

                ZipPart.Tip.naslov -> {
                    println("Save teorija dir: $teorijaDir")
                    var text = tesseract.doOCR(part.image.blackWhite())
                    text = (text.subSequence(0, minOf(text.length, 30))).toString()
                    teorijaDir = File(saveDir, text)
                    teorijaDir.mkdir()
                }

                ZipPart.Tip.teorija -> {
                    val teorijaFile = File(teorijaDir, "_teorija.png")
                    println("Save teorija: $teorijaFile")
                    part.image = part.image.getSafeSubimage(bb.x0, bb.y0, bb.width(), bb.height())
                    part.image.save(teorijaFile)
                }

                ZipPart.Tip.prazno -> {
                    throw Error("Prazen part image!")
                }
            }
        }
    }
}
