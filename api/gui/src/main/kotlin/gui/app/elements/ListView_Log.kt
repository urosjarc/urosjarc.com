package gui.app.elements;

import gui.domain.Log
import javafx.fxml.FXML
import javafx.scene.control.ListView
import org.koin.core.component.KoinComponent

abstract class ListView_Log_Ui : KoinComponent {
    @FXML
    lateinit var self: ListView<Log>
}

class ListView_Log : ListView_Log_Ui() {
    @FXML
    fun initialize() {
        println("init ListView_Log")
    }
}
