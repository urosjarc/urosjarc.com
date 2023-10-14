package data.gui2.parts;

import data.domain.Datoteka
import data.domain.ZipSlika
import javafx.fxml.FXML
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableColumn
import javafx.scene.control.TreeTableView
import javafx.scene.control.cell.TreeItemPropertyValueFactory
import javafx.scene.image.ImageView
import org.koin.core.component.KoinComponent
import java.nio.file.Paths

class ImageView_ZipSlika : KoinComponent {

    var zipSlika: ZipSlika? = null

    fun init(zipSlika: ZipSlika) {
        this.zipSlika = zipSlika
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
