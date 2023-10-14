package data.gui2.widgets

import javafx.fxml.FXML
import org.koin.core.component.KoinComponent

class Procesiranje_zip_slike : KoinComponent {

    lateinit var obracanje_rezanje_zip_slike_Controller: Obracanje_rezanje_zip_slike
    lateinit var anotiranje_zip_slike_Controller: Anotiranje_zip_slike

    @FXML
    fun initialize() {
        println("init Procesiranje_zip_slike")
    }

}
