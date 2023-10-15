package data.gui2.elements;

import data.base.Opazovan
import data.domain.Datoteka
import data.domain.ZipSlika
import data.extend.inputStream
import javafx.fxml.FXML
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableColumn
import javafx.scene.control.TreeTableView
import javafx.scene.control.cell.TreeItemPropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.koin.core.component.KoinComponent
import java.nio.file.Paths

class ImageView_ZipSlika : KoinComponent {
    fun init(zipSlika: ZipSlika) {
        this.self.image = Image(zipSlika.img.inputStream())
    }

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
