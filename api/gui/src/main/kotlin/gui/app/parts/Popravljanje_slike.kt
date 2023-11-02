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
    lateinit var automaticnoB: Button

    @FXML
    lateinit var rocnoB: Button

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
        this.rotacijaS.setOnMouseReleased { this.pripravi_sliko() }
        this.paddingS.setOnMouseReleased { this.pripravi_sliko() }
        this.automaticnoB.setOnAction { this.resetiraj_celotno_sliko_na_default_vrednosti() }
        this.rocnoB.setOnAction { this.posodobi_info_slike(reset = true); this.pripravi_sliko() }
        this.potrdiB.setOnAction { this.potrdi_trenutne_nastavitve() }
        this.preskociB.setOnAction { this.preskociSliko.value = this.slika }
    }

    fun init(slika: BufferedImage, popravi: Boolean) {
        this.slika = slika
        this.posodobi_info_slike(reset = true)
        if (popravi) this.resetiraj_celotno_sliko_na_default_vrednosti()
        else this.pripravi_sliko(procesiranje = false)
    }

    private fun pripravi_sliko(procesiranje: Boolean = true) {
        var img = this.slika
        if (procesiranje) img = this.rotiraj_in_odrezi_robove_slike()
        this.IMG.init(slika = img.binarna(negativ = true).narisiMrezo())
    }

    private fun posodobi_info_slike(reset: Boolean = false) {
        if(reset){
            this.rotacijaS.value = 0.0
            this.paddingS.value = 0.0
        }
        val r = "%.2f°".format(this.rotacijaS.value)
        val m = "%.2fpx".format(this.paddingS.value)
        this.infoL.text = "Rotacija: $r, Padding: $m"
    }

    private fun resetiraj_celotno_sliko_na_default_vrednosti() {
        this.log.info("Resetiraj rotacijo in padding trenutne slike.")
        val deskew = this.slika.poravnaj()
        val removeBorder = deskew.second.odstraniObrobo(maxWidth = 300)

        this.rotacijaS.value = deskew.first
        this.paddingS.value = removeBorder.first.toDouble()

        this.posodobi_info_slike()
        this.pripravi_sliko()
    }

    private fun potrdi_trenutne_nastavitve() {
        this.koncnaSlika.value = this.rotiraj_in_odrezi_robove_slike()
    }

    private fun rotiraj_in_odrezi_robove_slike(): BufferedImage {
        val m = this.paddingS.value.toInt()
        val img = this.slika.rotiraj(this.rotacijaS.value)
        return img.getSubimage(m, m, img.width - 2 * m, img.height - 2 * m)
    }
}
