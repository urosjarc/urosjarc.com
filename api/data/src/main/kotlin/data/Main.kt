package data

import net.sourceforge.tess4j.Tesseract
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.imageio.ImageIO

val tesseract = Tesseract()

fun init_tessaract() {
    tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");

    tesseract.setLanguage("slv")
    tesseract.setPageSegMode(11)
    tesseract.setOcrEngineMode(1)

    tesseract.setVariable("tessedit_create_hocr", "1")
    tesseract.setVariable("user_defined_dpi", "300");
}

fun zip_iterator(file: File): Sequence<BufferedImage> {
    val zipFile = ZipFile(file.absolutePath)
    val entries = zipFile.entries()

    return sequence {
        while (entries.hasMoreElements()) {
            val zipEntry: ZipEntry = entries.nextElement()
            val inputStream = zipFile.getInputStream(zipEntry)
            var bufferedImage = ImageIO.read(inputStream).deskew()

            for (i in 0..5) {
                bufferedImage = bufferedImage.blur()
            }

            yield(bufferedImage)
        }
        zipFile.close()
    }
}

fun main() {
    for (image in zip_iterator(File("/home/urosjarc/vcs/urosjarc.com2/api/data/src/main/resources/Omega11.zip"))) {

        for (y in 0 until image.height) {

            var minRed = image.width
            var maxRed = 0
            for (x in 50 until image.width/2) {
                val pixel = image.getHSV(x, y)
                if (pixel.is_red()) {
                    if (minRed > x) minRed = x
                    if (maxRed < x) maxRed = x
                }
            }

            if(maxRed - minRed < 150 && maxRed < 300 && minRed < 300){
                for (x in minRed..maxRed) {
                    image.setRGB(x, y, Color.CYAN.rgb)
                }
            }
        }
        image.show()
        Thread.sleep(5000)
    }

}
