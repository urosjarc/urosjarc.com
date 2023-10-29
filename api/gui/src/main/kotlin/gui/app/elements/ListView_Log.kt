package gui.app.elements

import gui.domain.Log
import javafx.fxml.FXML
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import org.koin.core.component.KoinComponent


abstract class ListView_Log_Ui : KoinComponent {
    @FXML
    lateinit var self: ListView<Log>
}

class ListView_Log : ListView_Log_Ui() {
    @FXML
    fun initialize() {
        this.self.setCellFactory {
            val cell: ListCell<Log> = object : ListCell<Log>() {
                override fun updateItem(item: Log?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty || item == null) {
                        this.graphic = null
                        this.style = null
                    } else {
                        this.text = item.data
                        this.style = when (item.tip) {
                            Log.Tip.INFO -> ""
                            Log.Tip.WARN -> "-fx-background-color: orange; -fx-text-fill: black;"
                            Log.Tip.ERROR -> "-fx-background-color: red"
                            Log.Tip.DEBUG -> "-fx-background-color: blue"
                        }
                    }
                }
            }
            cell
        }
    }
}
