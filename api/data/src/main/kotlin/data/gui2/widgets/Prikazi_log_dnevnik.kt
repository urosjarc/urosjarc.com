package data.gui2.widgets

import data.gui2.elements.ListView_Log
import data.services.LogService
import javafx.fxml.FXML
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Prikazi_log_dnevnik : KoinComponent {

    val log by this.inject<LogService>()

    @FXML
    lateinit var listView_log_Controller: ListView_Log

    @FXML
    fun initialize() {
        this.log.opazuj { this.listView_log_Controller.self.items.add(it) }
        println("init Prikazi_log_dnevnik")
    }
}
