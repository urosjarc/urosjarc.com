package gui.app.widgets

import gui.domain.Datoteka
import gui.domain.Slika
import gui.app.elements.ListView_Datoteka
import gui.app.elements.TreeTableView_Datoteka
import gui.services.LogService
import gui.services.ResouceService
import javafx.fxml.FXML
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.zip.ZipFile
import javax.imageio.ImageIO

class Izberi_zip_zvezek : KoinComponent {

    var zip_zvezek = gui.base.Opazovan<Datoteka?>(null)
    val resourseService by this.inject<ResouceService>()
    val log by this.inject<LogService>()

    @FXML
    lateinit var listView_datoteka_Controller: ListView_Datoteka

    @FXML
    lateinit var treeTableView_datoteka_Controller: TreeTableView_Datoteka


    @FXML
    fun initialize() {
        println("init Izberi_zip_zvezek")
        this.listView_datoteka_Controller.self.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
            this.init(datoteka = newValue)
        }

        this.listView_datoteka_Controller.init(this.resourseService.najdi_zip_datoteke())
    }

    private fun init(datoteka: Datoteka) {
        this.log.info("init: $datoteka")

        //Posodobi tree table.
        this.treeTableView_datoteka_Controller.init(datoteka)

        //Shrani zip zvezek
        this.zip_zvezek.value = datoteka
    }

    fun naslednja_zip_slika(): Slika {
        //Dobi zip datoteko
        val datoteka = this.zip_zvezek.value ?: throw this.log.error("Nobena datoteka ni bila se izbrana!")

        //Dobi zip entries.
        val zipFile = ZipFile(datoteka.file.absolutePath)
        val entries = zipFile.entries().toList()

        //Poglej koliko datotek si ze obdelal.
        val steviloOpravljenihDatotek = this.treeTableView_datoteka_Controller.self.root.children.size

        //Dobi zip datoteko ki je naslednja za obdelavo.
        val nasledjiZipEntry = entries[steviloOpravljenihDatotek]

        //Nalozi naslednji zip entry kot sliko
        val inputStream = zipFile.getInputStream(nasledjiZipEntry)
        val bufferedImage = ImageIO.read(inputStream)

        //Zapri zip file
        zipFile.close()

        return Slika(img = bufferedImage, index = steviloOpravljenihDatotek, size = entries.size)
    }

}
