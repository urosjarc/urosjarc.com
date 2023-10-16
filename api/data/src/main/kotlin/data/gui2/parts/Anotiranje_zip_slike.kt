package data.gui2.parts

import data.domain.ZipSlika
import data.extend.copy
import data.extend.drawGrid
import data.extend.drawSlikaAnnotations
import data.extend.negative
import data.gui2.elements.ImageView_BufferedImage
import data.services.LogService
import data.services.OcrService
import data.use_cases.Procesiraj_omego_sliko
import javafx.fxml.FXML
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Anotiranje_zip_slike : KoinComponent {

    val log by this.inject<LogService>()
    val procesirajOmegoSliko by this.inject<Procesiraj_omego_sliko>()
    val ocrService by this.inject<OcrService>()
    var data: ZipSlika? = null

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    fun initialize() {
        println("init Anotiranje_zip_slike")
    }

    fun init(zipSlika: ZipSlika) {
        this.data = zipSlika
        this.init_imageView()
    }

    private fun init_imageView() {
        this.log.info("init: ${this.data}")
        val img = this.data!!.img.copy()
        val anotacije = this.ocrService.google(image = img)
        val anotacijeStrani = this.procesirajOmegoSliko.zdaj(img = img, annos = anotacije)
        img.drawSlikaAnnotations(anotacijeStrani)
        this.imageView_bufferedImage_Controller.init(img = img)
    }

}
