package data.gui2.windows

import core.base.App
import data.gui2.widgets.Prikazi_zip_zvezek
import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import jfxtras.styles.jmetro.JMetro
import jfxtras.styles.jmetro.Style


class Procesiranje_zip_zvezkov : Application() {

    @FXML
    lateinit var prikazi_zip_zvezek_Controller: Prikazi_zip_zvezek

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
