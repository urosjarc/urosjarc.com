package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.base.Opazovan
import gui.domain.Slika
import gui.extend.*
import gui.services.LogService
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Slider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage

abstract class Popravljanje_slike_Ui : KoinComponent {
    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var infoL: Label

    @FXML
    lateinit var rotacijaS: Slider

    @FXML
    lateinit var paddingS: Slider

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    @FXML
    lateinit var preskociB: Button

    val IMG: ImageView_BufferedImage get() = this.imageView_bufferedImage_Controller
}

class Popravljanje_slike : Popravljanje_slike_Ui() {

    private val log by this.inject<LogService>()
    private lateinit var slika: Slika
    var koncnaSlika = Opazovan<Slika>()
    var preskociSliko = Opazovan<Slika>()

    @FXML
    fun initialize() {
        println("init Obracanje_rezanje_zip_slike")
        this.rotacijaS.valueProperty().addListener { _, _, _ -> this.init_infoL() }
        this.paddingS.valueProperty().addListener { _, _, _ -> this.init_infoL() }
        this.rotacijaS.setOnMouseReleased { this.init_imageView() }
        this.paddingS.setOnMouseReleased { this.init_imageView() }
        this.resetirajB.setOnAction { this.resetiraj() }
        this.potrdiB.setOnAction { this.potrdi() }
        this.preskociB.setOnAction { this.preskociSliko.value = this.slika }
    }

    fun init(slika: Slika) {
        this.slika = slika
        this.resetiraj()
    }

    private fun init_imageView() {
        this.log.info("init: ${this.infoL.text}")
        val img = this.process_data().binarna(negativ = true)
        img.narisiMrezo()
        this.IMG.init(img = img)
    }

    private fun init_infoL() {
        val r = "%.2f°".format(this.rotacijaS.value)
        val m = "%.2fpx".format(this.paddingS.value)
        this.infoL.text = "Rotacija: $r, Padding: $m"
    }

    private fun resetiraj() {
        this.log.info("resetiraj sliko")
        val deskew = this.slika.img.poravnaj()
        val removeBorder = deskew.second.odstraniObrobo(maxWidth = 300)

        this.rotacijaS.value = deskew.first
        this.paddingS.value = removeBorder.first.toDouble()

        this.init_infoL()
        this.init_imageView()
    }

    private fun potrdi() {
        this.koncnaSlika.value = this.slika.copy(img = this.process_data())
        this.log.info("potrdi sliko: ${this.koncnaSlika.value}")
    }

    private fun process_data(): BufferedImage {
        val m = this.paddingS.value.toInt()
        val img = this.slika.img.rotiraj(this.rotacijaS.value)
        return img.getSubimage(m, m, img.width - 2 * m, img.height - 2 * m)
    }
}
