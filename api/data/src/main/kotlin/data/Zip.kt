import org.imgscalr.Scalr
import java.awt.image.BufferedImage
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.imageio.ImageIO


class Zip {
    val parts = mutableListOf<Slika>()
    fun init(file: File) {
        val zipFile = ZipFile(file.absolutePath)
        val entries = zipFile.entries()

        while (entries.hasMoreElements()) {
            val zipEntry: ZipEntry = entries.nextElement()
            val inputStream = zipFile.getInputStream(zipEntry)
            val bufferedImage = ImageIO.read(inputStream)
            this.process(slika)
        }
        zipFile.close()
    }

    fun extract(slika: BufferedImage){

    }
}
