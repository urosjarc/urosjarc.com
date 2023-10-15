package data.gui2.parts

import data.domain.ZipSlika
import data.gui2.elements.ImageView_ZipSlika
import javafx.fxml.FXML
import java.awt.image.BufferedImage

class Obracanje_rezanje_zip_slike {

    @FXML
    lateinit var imageView_zipSlika_Controller: ImageView_ZipSlika

    fun init(zipSlika: ZipSlika){
        this.imageView_zipSlika_Controller.init(zipSlika)
    }

    @FXML
    fun initialize() {
        println("init Obracanje_rezanje_zip_slike")
    }
}
