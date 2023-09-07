package data.app

import net.sourceforge.tess4j.Tesseract
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.imageio.ImageIO

val tesseract = Tesseract()

fun init_tessaract() {
    tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");

    tesseract.setLanguage("slv")
    tesseract.setPageSegMode(11)
    tesseract.setOcrEngineMode(1)

    tesseract.setVariable("tessedit_create_hocr", "0")
    tesseract.setVariable("user_defined_dpi", "300");
}

fun zip_iterator(file: File, skip: Int): Sequence<BufferedImage> {
    val zipFile = ZipFile(file.absolutePath)
    val entries = zipFile.entries()

    return sequence {
        var i = 0
        while (entries.hasMoreElements()) {
            val zipEntry: ZipEntry = entries.nextElement()

            if (++i < skip) continue

            val inputStream = zipFile.getInputStream(zipEntry)


            val bufferedImage = ImageIO.read(inputStream)
            yield(bufferedImage)
        }
        zipFile.close()
    }
}
