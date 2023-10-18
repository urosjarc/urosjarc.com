package gui.app.parts

import gui.domain.Stran
import gui.app.elements.ImageView_BufferedImage
import gui.domain.Odsek
import gui.use_cases.Razrezi_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Rezanje_slike : KoinComponent {

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    val razrezi_stran by this.inject<Razrezi_stran>()
    var stran: Stran? = null
    var odseki = listOf<Odsek>()
    var index = 0

    fun init(stran: Stran) {
        this.stran = stran
        this.odseki = this.razrezi_stran.zdaj(stran = stran)

        this.potrdiB.setOnAction {
            this.index++
            this.init_imageView()
        }
        this.resetirajB.setOnAction {
            this.index--
            this.init_imageView()
        }

        this.init_imageView()
    }

    fun init_imageView() {
        if (this.index >= this.odseki.size)
            this.index = this.odseki.size - 1
        else if (this.index < 0)
            this.index = 0

        this.imageView_bufferedImage_Controller.init(this.odseki[this.index].img)
    }

    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
    }
}
