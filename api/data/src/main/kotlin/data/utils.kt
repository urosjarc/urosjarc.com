package data

import com.google.cloud.vision.v1.*
import com.google.protobuf.ByteString
import net.sourceforge.tess4j.Tesseract
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.imageio.ImageIO

private val tesseract = Tesseract()
private val vision = ImageAnnotatorClient.create()

fun init_tessaract() {
    tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata")

    tesseract.setLanguage("slv")
    tesseract.setPageSegMode(11)
    tesseract.setOcrEngineMode(1)

    tesseract.setVariable("tessedit_create_hocr", "0")
    tesseract.setVariable("user_defined_dpi", "300")
}

fun tessaract_ocr(image: BufferedImage): String {
    return tesseract.doOCR(image)
}

fun google_ocr(image: BufferedImage): MutableList<EntityAnnotation> {
    val baos = ByteArrayOutputStream()
    ImageIO.write(image, "png", baos)

    // [START vision_quickstart]
    val imgProto: ByteString = ByteString.copyFrom(baos.toByteArray())

    // Set up the Cloud Vision API request.
    val img = Image.newBuilder().setContent(imgProto).build()
    val feat = Feature.newBuilder().setType(Feature.Type.DOCUMENT_TEXT_DETECTION).build()
    val request = AnnotateImageRequest.newBuilder()
        .addFeatures(feat)
        .setImage(img)
        .build()

    // Call the Cloud Vision API and perform label detection on the image.
    val response = vision.batchAnnotateImages(arrayListOf(request))

    // Print the label annotations for the first response.
    val annons = response.responsesList[0].textAnnotationsList.toMutableList()
    return annons
}

fun zip_iterator(file: File, skip: Int, end: Int): Sequence<BufferedImage> {
    val zipFile = ZipFile(file.absolutePath)
    val entries = zipFile.entries()

    return sequence {
        var i = 0
        while (entries.hasMoreElements()) {
            val zipEntry: ZipEntry = entries.nextElement()

            if (++i < skip) continue
            if (i > end) break

            val inputStream = zipFile.getInputStream(zipEntry)
            val bufferedImage = ImageIO.read(inputStream)

            yield(bufferedImage)
        }
        zipFile.close()
    }
}
