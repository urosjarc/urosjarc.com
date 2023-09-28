package data.gui

import core.base.App
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.File

class Main : Application() {

    override fun start(stage: Stage) {
        App.pripravi_DI()
        val fxmlLoader = FXMLLoader(Main::class.java.getResource("/gui/main.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.scene = scene
        stage.show()
    }
}
