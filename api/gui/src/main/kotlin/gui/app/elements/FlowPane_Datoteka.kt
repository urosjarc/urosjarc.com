package gui.app.elements

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.FlowPane
import org.koin.core.component.KoinComponent

abstract class FlowPane_Datoteka_Ui : KoinComponent {
    @FXML
    lateinit var self: FlowPane
}

class FlowPane_Datoteka : FlowPane_Datoteka_Ui() {
    @FXML
    fun initialize() {
        println("init TreeTableView_Datoteka")
    }

    fun dodaj(ime: String, color: String, onAction: (String) -> Unit) {
        val button = Button(ime)
        button.userData = ime
        button.minWidth = 60.0
        button.style += ";-fx-background-color: $color;"
        button.setOnAction { onAction(ime) }
        this.self.children.add(button)
    }

    fun odstrani(ime: String) {
        this.self.children.removeIf { it.userData == ime }
    }

}
