package data.gui2.widgets

import data.domain.ZipSlika
import data.gui2.elements.ImageView_BufferedImage
import data.gui2.parts.Anotiranje_zip_slike
import data.gui2.parts.Obracanje_rezanje_zip_slike
import data.services.LogService
import javafx.fxml.FXML
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Procesiranje_zip_slike : KoinComponent {

    var zip_slika: ZipSlika? = null
    val log by this.inject<LogService>()

    @FXML
    lateinit var tabPane: TabPane

    @FXML
    lateinit var obracanjeRezanjeT: Tab

    @FXML
    lateinit var anotiranjeT: Tab

    @FXML
    lateinit var obracanje_rezanje_zip_slike_Controller: Obracanje_rezanje_zip_slike

    @FXML
    lateinit var anotiranje_zip_slike_Controller: Anotiranje_zip_slike


    fun init(zipSlika: ZipSlika) {
        this.log.info("init: $zipSlika")
        this.zip_slika = zipSlika
        this.obracanje_rezanje_zip_slike_Controller.init(zipSlika)
    }

    @FXML
    fun initialize() {
        println("init Procesiranje_zip_slike")
        this.obracanje_rezanje_zip_slike_Controller.finishedData.opazuj {
            this.anotiranje_zip_slike_Controller.init(it!!)
            this.tabPane.selectionModel.select(this.anotiranjeT)
        }
    }

}
