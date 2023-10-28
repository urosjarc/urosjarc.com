package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.base.Opazovan
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
    private lateinit var slika: BufferedImage
    var koncnaSlika = Opazovan<BufferedImage>()
    var preskociSliko = Opazovan<BufferedImage>()

    @FXML
    fun initialize() {
        this.rotacijaS.valueProperty().addListener { _, _, _ -> this.posodobi_info_slike() }
        this.paddingS.valueProperty().addListener { _, _, _ -> this.posodobi_info_slike() }
        this.rotacijaS.setOnMouseReleased { this.pripravi_sliko_narisi_mrezo_in_jo_prikazi() }
        this.paddingS.setOnMouseReleased { this.pripravi_sliko_narisi_mrezo_in_jo_prikazi() }
        this.resetirajB.setOnAction { this.resetiraj_celotno_sliko_na_default_vrednosti() }
        this.potrdiB.setOnAction { this.potrdi_trenutne_nastavitve() }
        this.preskociB.setOnAction { this.preskociSliko.value = this.slika }
    }

    fun init(slika: BufferedImage) {
        this.slika = slika
        this.resetiraj_celotno_sliko_na_default_vrednosti()
    }

    private fun pripravi_sliko_narisi_mrezo_in_jo_prikazi() {
        this.log.info("init: ${this.infoL.text}")
        val img = this.rotiraj_in_odrezi_robove_slike().binarna(negativ = true)
        img.narisiMrezo()
        this.IMG.init(slika = img)
    }

    private fun posodobi_info_slike() {
        val r = "%.2f°".format(this.rotacijaS.value)
        val m = "%.2fpx".format(this.paddingS.value)
        this.infoL.text = "Rotacija: $r, Padding: $m"
    }

    private fun resetiraj_celotno_sliko_na_default_vrednosti() {
        this.log.info("resetiraj sliko")
        val deskew = this.slika.poravnaj()
        val removeBorder = deskew.second.odstraniObrobo(maxWidth = 300)

        this.rotacijaS.value = deskew.first
        this.paddingS.value = removeBorder.first.toDouble()

        this.posodobi_info_slike()
        this.pripravi_sliko_narisi_mrezo_in_jo_prikazi()
    }

    private fun potrdi_trenutne_nastavitve() {
        this.koncnaSlika.value = this.rotiraj_in_odrezi_robove_slike()
        this.log.info("potrdi sliko: ${this.koncnaSlika.value}")
    }

    private fun rotiraj_in_odrezi_robove_slike(): BufferedImage {
        val m = this.paddingS.value.toInt()
        val img = this.slika.rotiraj(this.rotacijaS.value)
        return img.getSubimage(m, m, img.width - 2 * m, img.height - 2 * m)
    }
}
