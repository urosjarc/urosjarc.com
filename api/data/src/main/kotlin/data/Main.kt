package data

import Zip
import java.io.File

fun main() {
    val url = Zip::class.java.getResource("Omega11.zip")
    val file = File(url?.toURI() ?: throw Error("$url not exists!"))
    val checkpoint = File("Omega11.bin")
    val dir = File(file.parentFile.absolutePath + "/omega11")

    fromStart(file, checkpoint)
//    fromCheckpoint(checkpoint, dir)
}

fun fromStart(zipFile: File, checkpointFile: File): Zip {
    val zip = Zip()
    zip.init(file = zipFile)
    zip.saveCheckpoint(file = checkpointFile)
    return zip
}

fun fromCheckpoint(checkpointFile: File, saveDir: File) {
    val zip = Zip.loadCheckpoint(file = checkpointFile)
    zip.process()
    zip.saveParts(saveDir)
}
