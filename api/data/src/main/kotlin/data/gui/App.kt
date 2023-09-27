package data.gui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class App : Application() {

    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(App::class.java.getResource("/gui/app.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(App::class.java)
}
