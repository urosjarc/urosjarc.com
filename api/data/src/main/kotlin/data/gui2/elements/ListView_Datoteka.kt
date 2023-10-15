package data.gui2.elements;

import data.base.Opazovan
import data.domain.Datoteka
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.ListView
import org.koin.core.component.KoinComponent

class ListView_Datoteka : KoinComponent {

    fun init(datoteke: Collection<Datoteka>) {
        this.self.items = FXCollections.observableArrayList(datoteke)
    }

    @FXML
    fun initialize() {
        println("init ListView_Datoteka")
    }

    @FXML
    lateinit var self: ListView<Datoteka>
}
