package data.gui2.widgets

import data.domain.Datoteka
import data.domain.ZipSlika
import data.extend.show
import data.gui2.parts.ListView_Datoteka
import data.gui2.parts.TreeTableView_Datoteka
import data.services.ResouceService
import javafx.fxml.FXML
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.zip.ZipFile
import javax.imageio.ImageIO

class Prikazi_zip_zvezek : KoinComponent {

    val resourseService by this.inject<ResouceService>()

    lateinit var listView_datoteka_Controller: ListView_Datoteka
    lateinit var treeTableView_datoteka_Controller: TreeTableView_Datoteka

    @FXML
    fun initialize() {
        println("init Prikazi_zip_zvezek")
        this.listView_datoteka_Controller.self.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
            this.init(datoteka = newValue)
        }

        this.listView_datoteka_Controller.init(this.resourseService.najdi_zip_datoteke())

    }

    private fun init(datoteka: Datoteka) {
        this.treeTableView_datoteka_Controller.init(datoteka)

        val zipFile = ZipFile(datoteka.file.absolutePath)
        val entries = zipFile.entries().toList()
        val inputStream = zipFile.getInputStream(entries[0])
        val bufferedImage = ImageIO.read(inputStream)
        bufferedImage.show()
        zipFile.close()
    }

}
