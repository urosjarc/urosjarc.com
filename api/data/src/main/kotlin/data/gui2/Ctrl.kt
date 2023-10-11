package data.gui2;

import data.domain.Datoteka
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.ProgressBar
import javafx.scene.control.TreeTableView

class Ctrl {
    val utils = Utils()

    @FXML
    lateinit var zipDatotekeLW: ListView<Datoteka>

    @FXML
    lateinit var zipDatotekaPB: ProgressBar

    @FXML
    lateinit var zipDatotekaTTV: TreeTableView<Datoteka>

    @FXML
    lateinit var zacniB: Button

    @FXML
    lateinit var logsLV: ListView<String>

    @FXML
    fun initialize() {
    }
}
