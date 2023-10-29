package gui.services

import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.protobuf.ByteString
import gui.domain.Anotacija
import gui.domain.Okvir
import gui.domain.Vektor
import net.sourceforge.tess4j.Tesseract
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

class OcrService {
    private val vision = ImageAnnotatorClient.create()
    private val tesseract = Tesseract()

    fun init_tessaract() {
        tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata")

        tesseract.setLanguage("slv")
        tesseract.setPageSegMode(11)
        tesseract.setOcrEngineMode(1)

        tesseract.setVariable("tessedit_create_hocr", "0")
        tesseract.setVariable("user_defined_dpi", "300")
    }

    fun tessaract(image: BufferedImage): String {
        return tesseract.doOCR(image)
    }

    fun google(image: BufferedImage): List<Anotacija> {
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
        annons.removeAt(0)

        //Mapiraj v domenski objekt
        return annons.map {
            val xs = mutableListOf<Int>()
            val ys = mutableListOf<Int>()

            for (vertex in it.boundingPoly.verticesList) {
                xs.add(vertex.x)
                ys.add(vertex.y)
            }

            Anotacija(
                okvir = Okvir(start = Vektor(x = xs.min(), y = ys.min()), end = Vektor(x = xs.max(), y = ys.max())),
                text = it.description, tip = Anotacija.Tip.NEZNANO
            )
        }
    }
}
