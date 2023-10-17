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
        val ratio = img.height / img.width.toDouble()
        this.self.image = Image(img.inputStream())

        this.backgroundP.maxWidth = this.backgroundP.prefHeight / ratio
        this.backgroundP.minWidth = this.backgroundP.prefHeight / ratio
        this.backgroundP.prefWidth = this.backgroundP.prefHeight / ratio
        this.self.fitWidth = this.self.fitHeight / ratio

        println(this.backgroundP.prefWidth)
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
