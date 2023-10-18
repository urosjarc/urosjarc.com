package gui.app.windows

import gui.app.widgets.Izberi_zip_zvezek
import gui.app.widgets.Prikazi_log_dnevnik
import gui.app.widgets.Procesiranje_slike
import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import jfxtras.styles.jmetro.JMetro
import jfxtras.styles.jmetro.Style


class Procesiranje_zip_zvezkov : Application() {

    @FXML
    lateinit var izberi_zip_zvezek_Controller: Izberi_zip_zvezek

    @FXML
    lateinit var procesiranje_slike_Controller: Procesiranje_slike

    @FXML
    lateinit var prikazi_log_dnevnik_Controller: Prikazi_log_dnevnik

    @FXML
    fun initialize() {
        println("init Procesiranje_zip_zvezkov")
        this.izberi_zip_zvezek_Controller.zip_zvezek.opazuj {
            this.procesiranje_slike_Controller.init(
                this.izberi_zip_zvezek_Controller.naslednja_zip_slika()
            )
        }
    }

    override fun start(stage: Stage) {
        println(gui.base.Env.TIME_ZONE)
        gui.base.App.pripravi_DI()
        val fxmlLoader = FXMLLoader(this.javaClass.getResource("Procesiranje_zip_zvezkov.fxml"))
        val scene = Scene(fxmlLoader.load())
        val jMetro = JMetro(Style.DARK)
        jMetro.scene = scene
        stage.scene = scene
        stage.show()
    }
}
