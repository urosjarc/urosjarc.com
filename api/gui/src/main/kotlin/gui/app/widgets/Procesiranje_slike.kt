package gui.app.widgets

import gui.app.parts.Anotiranje_slike
import gui.app.parts.Popravljanje_slike
import gui.domain.Stran
import javafx.fxml.FXML
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import java.awt.image.BufferedImage

abstract class Procesiranje_slike_Ui : KoinComponent {
    @FXML
    lateinit var tabPane: TabPane

    @FXML
    lateinit var popravljanjeT: Tab

    @FXML
    lateinit var anotiranjeT: Tab

    @FXML
    lateinit var popravljanje_slike_Controller: Popravljanje_slike

    @FXML
    lateinit var anotiranje_slike_Controller: Anotiranje_slike


    val POP get() = this.popravljanje_slike_Controller
    val ANO get() = this.anotiranje_slike_Controller
}

enum class BarveAnotacij(val value: Color) {
    NOGA(Color.BLACK),
    NALOGE(Color.DEEPSKYBLUE),
    PODNALOGE(Color.MAGENTA),
    NASLOV(Color.GREEN),
    TEORIJA(Color.BLUEVIOLET),
    DODATNO(Color.RED)
}

class Procesiranje_slike : Procesiranje_slike_Ui() {
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
