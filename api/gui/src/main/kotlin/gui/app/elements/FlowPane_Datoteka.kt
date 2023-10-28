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
    fun dodaj(ime: String, barva: String, onAction: () -> Unit) {
        val button = Button(ime)
        button.userData = ime
        button.minWidth = 60.0
        button.style = ";-fx-background-color: $barva;"
        button.setOnAction { onAction() }
        this.self.children.add(button)
    }

    fun posodobi(ime: String, color: String) {
        this.self.children.find { it.userData == ime }?.style = ";-fx-background-color: $color;"
    }

    fun odstrani(ime: String) {
        this.self.children.removeIf { it.userData == ime }
    }

    fun reset() {
        this.self.children.clear()
    }

}
