package gui.app.parts

import gui.domain.Stran
import gui.app.elements.ImageView_BufferedImage
import javafx.fxml.FXML
import javafx.scene.control.Button
import org.koin.core.component.KoinComponent
import java.awt.image.BufferedImage

class Rezanje_slike : KoinComponent {

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    var data: Stran? = null
    var dataDeli = listOf<BufferedImage>()
    var dataIndex = 0

    fun init(anotacijeStrani: Stran) {
        this.data = anotacijeStrani
        this.dataDeli = anotacijeStrani.razrezi()

        this.potrdiB.setOnAction {
            this.dataIndex++
            this.init_imageView()
        }
        this.resetirajB.setOnAction {
            this.dataIndex--
            this.init_imageView()
        }

        this.init_imageView()
    }

    fun init_imageView() {
        this.imageView_bufferedImage_Controller.init(this.dataDeli[this.dataIndex])
    }

    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
    }
}
