package gui.app.widgets

import gui.app.parts.Anotiranje_slike
import gui.app.parts.Popravljanje_slike
import gui.app.parts.Rezanje_slike
import gui.services.LogService
import gui.use_cases.Razrezi_stran
import javafx.fxml.FXML
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage

abstract class Procesiranje_slike_Ui : KoinComponent {
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

    val POP get() = this.popravljanje_slike_Controller
    val ANO get() = this.anotiranje_slike_Controller
    val REZ get() = this.rezanje_slike_Controller
}

class Procesiranje_slike : Procesiranje_slike_Ui() {
    private val log by this.inject<LogService>()
    private val razrezi_stran by this.inject<Razrezi_stran>()
    private lateinit var slika: BufferedImage


    fun init(slika: BufferedImage) {
        this.log.info("init: $slika")
        this.slika = slika
        this.POP.init(slika)
        this.tabPane.selectionModel.select(this.popravljanjeT)
    }

    @FXML
    fun initialize() {
        println("init Procesiranje_zip_slike")
        this.POP.koncnaSlika.opazuj {
            this.ANO.init(it)
            this.tabPane.selectionModel.select(this.anotiranjeT)
        }
        this.ANO.potrdiB.setOnAction {
            this.REZ.init(slika=this.ANO.img, stran = this.ANO.stran)
            this.tabPane.selectionModel.select(this.rezanjeT)
        }
    }


}
