package data.gui2.widgets

import data.gui2.elements.ImageView_ZipSlika
import data.gui2.elements.ListView_Log
import javafx.fxml.FXML

class Prikazi_log_dnevnik {

    @FXML
    lateinit var listView_log_Controller: ListView_Log

    @FXML
    fun initialize() {
        println("init Prikazi_log_dnevnik")
    }
}
