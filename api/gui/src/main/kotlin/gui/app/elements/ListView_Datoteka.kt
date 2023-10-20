package gui.app.elements

import gui.domain.Datoteka
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.ListView
import org.koin.core.component.KoinComponent

abstract class ListView_Datoteka_Ui : KoinComponent {
    @FXML
    lateinit var self: ListView<Datoteka>
}

class ListView_Datoteka : ListView_Datoteka_Ui() {

    @FXML
    fun initialize() {
        println("init ListView_Datoteka")
    }

    fun init(datoteke: Collection<Datoteka>) {
        this.self.items = FXCollections.observableArrayList(datoteke)
    }


}
