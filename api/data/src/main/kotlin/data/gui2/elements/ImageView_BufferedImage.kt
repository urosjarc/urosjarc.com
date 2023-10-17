package data.gui2.elements;

import data.base.Opazovan
import data.extend.inputStream
import javafx.fxml.FXML
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import org.koin.core.component.KoinComponent
import java.awt.image.BufferedImage

class ImageView_BufferedImage : KoinComponent {
    @FXML
    lateinit var backgroundP: Pane

    @FXML
    lateinit var self: ImageView

    companion object {
        var visina = Opazovan(900.0)
    }

    fun init(img: BufferedImage) {
        this.self.image = Image(img.inputStream())
        this.nastaviVisino(visina.value)
    }

    fun nastaviVisino(v: Double) {
        val img = this.self.image
        val ratio = img.height / img.width

        this.backgroundP.let {
            it.maxHeight = v
            it.minHeight = v
            it.maxWidth = v / ratio
            it.minWidth = v / ratio
        }
        this.self.let {
            it.fitWidth = v / ratio
            it.fitHeight = v
        }

        visina.value = v
    }


    @FXML
    fun initialize() {
        println("init ImageView_ZipSlika")
        this.self.setOnScroll {
            this.nastaviVisino(visina.value + it.deltaY)
        }

    }

    @FXML
    fun clicked() {
        println("clicked")
    }
}
