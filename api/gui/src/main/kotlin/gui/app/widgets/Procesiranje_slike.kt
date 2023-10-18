package gui.app.widgets

import gui.domain.Slika
import gui.app.parts.Anotiranje_slike
import gui.app.parts.Popravljanje_slike
import gui.app.parts.Rezanje_slike
import gui.services.LogService
import javafx.fxml.FXML
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Procesiranje_slike : KoinComponent {

    var slika: Slika? = null
    val log by this.inject<LogService>()

    @FXML
    lateinit var tabPane: TabPane

    @FXML
    lateinit var popravljanjeT: Tab

    @FXML
    lateinit var anotiranjeT: Tab

    @FXML
    lateinit var rezanjeT: Tab

    @FXML
    lateinit var popravljanje_slike_Controller: Popravljanje_slike

    @FXML
    lateinit var anotiranje_slike_Controller: Anotiranje_slike

    @FXML
    lateinit var rezanje_slike_Controller: Rezanje_slike


    fun init(slika: Slika) {
        this.log.info("init: $slika")
        this.slika = slika
        this.popravljanje_slike_Controller.init(slika)
    }

    @FXML
    fun initialize() {
        println("init Procesiranje_zip_slike")
        this.popravljanje_slike_Controller.koncnaSlika.opazuj {
            this.anotiranje_slike_Controller.init(it!!)
            this.tabPane.selectionModel.select(this.anotiranjeT)
        }
        this.anotiranje_slike_Controller.potrdiB.setOnAction {
            this.rezanje_slike_Controller.init(
                this.anotiranje_slike_Controller.stran!!
            )
            this.tabPane.selectionModel.select(this.rezanjeT)
        }
    }

}
