package gui.app.parts

import gui.domain.Stran
import gui.app.elements.ImageView_BufferedImage
import gui.use_cases.Razrezi_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage

class Rezanje_slike : KoinComponent {

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    val razrezi_stran by this.inject<Razrezi_stran>()
    var stran: Stran? = null
    var imgs = listOf<BufferedImage>()
    var imgIndex = 0

    fun init(stran: Stran) {
        this.stran = stran
        this.imgs = this.razrezi_stran.zdaj(stran = stran)

        this.potrdiB.setOnAction {
            this.imgIndex++
            this.init_imageView()
        }
        this.resetirajB.setOnAction {
            this.imgIndex--
            this.init_imageView()
        }

        this.init_imageView()
    }

    fun init_imageView() {
        if (this.imgIndex >= this.imgs.size)
            this.imgIndex = this.imgs.size - 1
        else if (this.imgIndex < 0)
            this.imgIndex = 0

        this.imageView_bufferedImage_Controller.init(this.imgs[this.imgIndex])
    }

    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
    }
}
