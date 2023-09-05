import data.Ocr
import data.deskew
import java.awt.image.BufferedImage
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.imageio.ImageIO

data class ZipPart(
    val slika: BufferedImage,
    val tip: Tip
) {

    enum class Tip { NASLOV, TEORIJA, NALOGA }
}

class Zip {
    val parts = mutableListOf<ZipPart>()

    fun init(file: File) {
        val zipFile = ZipFile(file.absolutePath)
        val entries = zipFile.entries()

        while (entries.hasMoreElements()) {
            val zipEntry: ZipEntry = entries.nextElement()
            println(zipEntry)
            val inputStream = zipFile.getInputStream(zipEntry)
            val bufferedImage = ImageIO.read(inputStream).deskew()
            Ocr.detect(bufferedImage)
        }
        zipFile.close()
    }
}
