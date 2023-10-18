package data.gui2.widgets

import data.domain.Slika
import data.gui2.parts.Anotiranje_slike
import data.gui2.parts.Popravljanje_slike
import data.gui2.parts.Rezanje_slike
import data.services.LogService
import javafx.fxml.FXML
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Procesiranje_slike : KoinComponent {

    var zip_slika: Slika? = null
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


    fun init(zipSlika: Slika) {
        this.log.info("init: $zipSlika")
        this.zip_slika = zipSlika
        this.popravljanje_slike_Controller.init(zipSlika)
    }

    @FXML
    fun initialize() {
        println("init Procesiranje_zip_slike")
        this.popravljanje_slike_Controller.finishedData.opazuj {
            this.anotiranje_slike_Controller.init(it!!)
            this.tabPane.selectionModel.select(this.anotiranjeT)
        }
        this.anotiranje_slike_Controller.potrdiB.setOnAction {
            this.rezanje_slike_Controller.init(
                this.anotiranje_slike_Controller.anotacijeStrani!!
            )
            this.tabPane.selectionModel.select(this.rezanjeT)
        }
    }

}
