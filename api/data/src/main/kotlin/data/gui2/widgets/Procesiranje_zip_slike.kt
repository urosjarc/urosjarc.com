package data.gui2.widgets

import data.domain.ZipSlika
import data.gui2.parts.Anotiranje_zip_slike
import data.gui2.parts.Obracanje_rezanje_zip_slike
import javafx.fxml.FXML
import org.koin.core.component.KoinComponent

class Procesiranje_zip_slike : KoinComponent {

    var zip_slika: ZipSlika? = null

    @FXML
    lateinit var obracanje_rezanje_zip_slike_Controller: Obracanje_rezanje_zip_slike

    @FXML
    lateinit var anotiranje_zip_slike_Controller: Anotiranje_zip_slike

    fun init(zipSlika: ZipSlika) {
        this.zip_slika = zipSlika

        this.obracanje_rezanje_zip_slike_Controller.init(zipSlika)
    }

    @FXML
    fun initialize() {
        println("init Procesiranje_zip_slike")
    }

}
