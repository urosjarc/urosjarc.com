package data.gui2.parts

import data.domain.AnotacijeStrani
import data.domain.ZipSlika
import data.gui2.elements.ImageView_BufferedImage
import javafx.fxml.FXML
import javafx.scene.control.Button
import org.koin.core.component.KoinComponent

class Rezanje_zip_slike : KoinComponent {

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    var data: AnotacijeStrani? = null
    fun init(anotacijeStrani: AnotacijeStrani) {
        this.data = anotacijeStrani
        this.imageView_bufferedImage_Controller.init(this.data!!.zipSlika.img)
    }

    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
    }
}
