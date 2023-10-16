package data.gui2.parts

import data.base.Opazovan
import data.domain.ZipSlika
import data.extend.*
import data.gui2.elements.ImageView_BufferedImage
import data.services.LogService
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Slider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage

class Obracanje_rezanje_zip_slike : KoinComponent {

    val log by this.inject<LogService>()
    var data: ZipSlika? = null
    var finishedData = Opazovan<ZipSlika?>(null)

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var rotacijaS: Slider

    @FXML
    lateinit var paddingS: Slider

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    @FXML
    lateinit var infoL: Label

    fun init(zipSlika: ZipSlika) {
        this.data = zipSlika
        this.resetiraj()
    }

    private fun resetiraj() {
        this.log.info("resetiraj sliko")
        val deskew = this.data!!.img.deskew()
        val removeBorder = deskew.second.removeBorder(maxWidth = 300)

        this.rotacijaS.value = deskew.first
        this.paddingS.value = removeBorder.first.toDouble()

        this.init_infoL()
        this.init_imageView()
    }

    private fun potrdi() {
        this.finishedData.value = this.data!!.copy(img=this.process_data())
        this.log.info("potrdi sliko: ${this.finishedData.value}")
    }

    private fun process_data(): BufferedImage {
        val m = this.paddingS.value.toInt()
        var img = this.data!!.img.rotate(this.rotacijaS.value)
        return img.getSubimage(m, m, img.width - 2 * m, img.height - 2 * m)
    }

    private fun init_imageView() {
        this.log.info("init: ${this.infoL.text}")
        val img = this.process_data()!!.negative()
        img.drawGrid()
        this.imageView_bufferedImage_Controller.init(img)
    }

    private fun init_infoL() {
        val r = "%.2f°".format(this.rotacijaS.value)
        val m = "%.2fpx".format(this.paddingS.value)
        this.infoL.text = "Rotacija: $r, Padding: $m"
    }

    @FXML
    fun initialize() {
        println("init Obracanje_rezanje_zip_slike")
        this.rotacijaS.valueProperty().addListener { _, _, _ -> this.init_infoL() }
        this.paddingS.valueProperty().addListener { _, _, _ -> this.init_infoL() }
        this.rotacijaS.setOnMouseReleased { this.init_imageView() }
        this.paddingS.setOnMouseReleased { this.init_imageView() }
        this.resetirajB.setOnAction { this.resetiraj() }
        this.potrdiB.setOnAction { this.potrdi() }
    }
}
