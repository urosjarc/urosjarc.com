package gui.app.elements;

import gui.domain.Log
import javafx.fxml.FXML
import javafx.scene.control.ListView
import org.koin.core.component.KoinComponent

class ListView_Log : KoinComponent {

    @FXML
    lateinit var self: ListView<Log>

    @FXML
    fun initialize() {
        println("init ListView_Log")
    }
}
