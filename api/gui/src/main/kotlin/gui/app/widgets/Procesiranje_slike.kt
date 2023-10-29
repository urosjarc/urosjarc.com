package gui.app.widgets

import gui.app.parts.Anotiranje_slike
import gui.app.parts.Popravljanje_slike
import gui.domain.Stran
import gui.services.LogService
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


    val POP get() = this.popravljanje_slike_Controller
    val ANO get() = this.anotiranje_slike_Controller
}

class Procesiranje_slike : Procesiranje_slike_Ui() {
    private val log by this.inject<LogService>()
    private lateinit var slika: BufferedImage
    var stSlike: Int = -1


    fun init(stSlike: Int, slika: BufferedImage, stran: Stran?) {
        this.stSlike = stSlike
        this.slika = slika
        this.POP.init(slika, popravi = stran == null)
        val tab = if (stran == null) {
            this.popravljanjeT
        } else {
            this.ANO.init(slika = slika, stran = stran)
            this.anotiranjeT
        }
        this.tabPane.selectionModel.select(tab)
    }

    @FXML
    fun initialize() {
        this.POP.koncnaSlika.opazuj {
            this.ANO.init(it, stran = null)
            this.tabPane.selectionModel.select(this.anotiranjeT)
        }
    }


}
