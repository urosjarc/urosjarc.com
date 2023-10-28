package gui.app.widgets

import gui.app.elements.ListView_Log
import gui.services.LogService
import javafx.fxml.FXML
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class Prikazi_log_dnevnik_Ui : KoinComponent {
    @FXML
    lateinit var listView_log_Controller: ListView_Log
    val LIST get() = this.listView_log_Controller
}
class Prikazi_log_dnevnik : Prikazi_log_dnevnik_Ui() {

    val log by this.inject<LogService>()


    @FXML
    fun initialize() {
        this.log.opazuj { this.LIST.self.items.add(it) }
    }
}
