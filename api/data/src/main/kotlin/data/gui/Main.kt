package data.gui

import core.base.App
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import jfxtras.styles.jmetro.JMetro
import jfxtras.styles.jmetro.Style


class Main : Application() {

    override fun start(stage: Stage) {
        App.pripravi_DI()
        val fxmlLoader = FXMLLoader(Main::class.java.getResource("/gui/main.fxml"))
        val scene = Scene(fxmlLoader.load())
        val jMetro = JMetro(Style.DARK)
        jMetro.scene = scene
        stage.scene = scene
        stage.show()
    }
}
