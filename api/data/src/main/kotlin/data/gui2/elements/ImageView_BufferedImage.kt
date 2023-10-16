package data.gui2.elements;

import data.domain.ZipSlika
import data.extend.inputStream
import javafx.fxml.FXML
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import org.koin.core.component.KoinComponent
import java.awt.image.BufferedImage

class ImageView_BufferedImage : KoinComponent {
    fun init(img: BufferedImage) {
        this.self.image = Image(img.inputStream())
    }

    @FXML
    lateinit var backgroundP: Pane

    @FXML
    lateinit var self: ImageView

    @FXML
    fun initialize() {
        println("init ImageView_ZipSlika")
    }

    @FXML
    fun clicked() {
        println("clicked")
    }
}
