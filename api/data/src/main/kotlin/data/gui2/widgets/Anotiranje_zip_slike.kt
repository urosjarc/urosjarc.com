package data.gui2.widgets

import data.domain.Datoteka
import data.extend.show
import data.gui2.parts.ImageView_ZipSlika
import data.gui2.parts.ListView_Datoteka
import data.gui2.parts.TreeTableView_Datoteka
import data.services.ResouceService
import javafx.fxml.FXML
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.zip.ZipFile
import javax.imageio.ImageIO

class Anotiranje_zip_slike : KoinComponent {

    lateinit var imageView_zipSlika_Controller: ImageView_ZipSlika

    @FXML
    fun initialize() {
        println("init Anotiranje_zip_slike")
    }

}
