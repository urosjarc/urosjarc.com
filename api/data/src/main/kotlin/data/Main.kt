package data

import net.sourceforge.tess4j.Tesseract
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
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
            val bufferedImage = ImageIO.read(inputStream).blackWhite().deskew()
            yield(bufferedImage)
        }
        zipFile.close()
    }
}

fun hocr(bufferedImage: BufferedImage): Document {
    val html = tesseract.doOCR(bufferedImage)
    return Jsoup.parse(html)
}

fun image_iterator(document: Document): Sequence<ImageEle> {
    return sequence {
        for (ocr_line in document.body().select("span.ocr_line")) {

            val bbox = ocr_line.attr("title")
                .split(";").first()
                .split(" ").subList(1, 5)

            val box = Box(
                startX = bbox[0].toInt(),
                startY = bbox[1].toInt(),
                endX = bbox[2].toInt(),
                endY = bbox[3].toInt(),
            )

            val imageEle = ImageEle(tip = ImageEle.Tip.NALOGA, text = ocr_line.text(), box = box)

            yield(imageEle)

        }
    }
}

data class Box(
    val startX: Int,
    val endX: Int,
    val startY: Int,
    val endY: Int
)

data class ImageEle(
    val tip: Tip,
    val text: String,
    val box: Box
) {
    enum class Tip { NALOGA, TEMATIKA, TEORIJA }
}

fun main() {

    init_tessaract()

    var naloga = 0
    var count = 0
    val recognized = mutableListOf<Int>()

    for (image in zip_iterator(File("/home/urosjarc/vcs/urosjarc.com2/api/data/src/main/resources/Omega11.zip"))) {
        if (count++ < 6) continue

        val doc = hocr(image)

        for (element in image_iterator(doc)) {

            if (element.box.startX > 200) {
                continue
            }

            val dotSplit = element.text.split(" ").first().split(".")

            val first = dotSplit.first()
            val second = dotSplit.getOrNull(1)

            val firstNum = first.toIntOrNull() ?: -1
            val secondNum = second?.toIntOrNull() ?: -1

            if (secondNum > 0) {
                println("Tematika: ${element.text}")
            } else {
                val diff = firstNum - naloga
                if (diff in 1..20) {
                    println("\t - Naloga: ${element.text}")
                    recognized.add(firstNum)
                    naloga = firstNum
                }
            }

        }
    }

    val max = recognized.max()
    val min = recognized.min()

    println("Report... ${max - recognized.size} missing")
    for (i in min..max) {
        if (!recognized.contains(i)) {
            println("Not found $i")
        }
    }


}
