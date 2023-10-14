package data.gui2.windows

import core.base.App
import data.gui2.widgets.Izberi_zip_zvezek
import data.gui2.widgets.Prikazi_log_dnevnik
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
    lateinit var prikazi_log_dnevnik_Controller: Prikazi_log_dnevnik
    @FXML
    lateinit var procesiranje_zip_zvezkov_Controller: Procesiranje_zip_zvezkov

    @FXML
    fun initialize() {
        println("init Procesiranje_zip_zvezkov")
    }

    override fun start(stage: Stage) {
        App.pripravi_DI()
        val fxmlLoader = FXMLLoader(this.javaClass.getResource("Procesiranje_zip_zvezkov.fxml"))
        val scene = Scene(fxmlLoader.load())
        val jMetro = JMetro(Style.DARK)
        jMetro.scene = scene
        stage.scene = scene
        stage.show()
    }
}
