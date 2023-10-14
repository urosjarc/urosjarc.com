package data.gui2.parts;

import data.domain.Datoteka
import data.domain.Log
import data.domain.ZipSlika
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableColumn
import javafx.scene.control.TreeTableView
import javafx.scene.control.cell.TreeItemPropertyValueFactory
import javafx.scene.image.ImageView
import org.koin.core.component.KoinComponent
import java.nio.file.Paths

class ListView_Log : KoinComponent {

    @FXML
    lateinit var self: ListView<Log>

    @FXML
    fun initialize() {
        println("init ListView_Log")
    }
}
