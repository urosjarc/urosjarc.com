package data

import net.sourceforge.tess4j.Tesseract
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.awt.image.BufferedImage


object Ocr {
    val tesseract = Tesseract()
    init {
        tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");
        tesseract.setLanguage("slv")
        tesseract.setPageSegMode(11)
        tesseract.setVariable("tessedit_create_hocr", "1")
//        tesseract.setOcrEngineMode(2)
    }

    fun detect(bufferedImage: BufferedImage){
        val html = tesseract.doOCR(bufferedImage)
        val doc: Document = Jsoup.parse(html)

        val ocr_careas = doc.select("div.ocr_carea")
        for(ocr_carea in ocr_careas){
            for(ocr_par in ocr_careas.select("p.ocr_par")){
                println(ocr_par)
            }
        }
    }
}
