package gui.app.widgets

import gui.app.elements.FlowPane_Datoteka
import gui.app.elements.ListView_Datoteka
import gui.base.Opazovan
import gui.domain.Datoteka
import gui.services.LogService
import gui.services.ResouceService
import gui.use_cases.Najdi_vse_slike
import javafx.fxml.FXML
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

abstract class Izberi_zip_zvezek_Ui : KoinComponent {
    @FXML
    lateinit var listView_datoteka_Controller: ListView_Datoteka

    @FXML
    lateinit var flowPane_datoteka_Controller: FlowPane_Datoteka
    val LIST get() = this.listView_datoteka_Controller
    val FLOW get() = this.flowPane_datoteka_Controller
}

class Izberi_zip_zvezek : Izberi_zip_zvezek_Ui() {
    val resourseService by this.inject<ResouceService>()
    val najdi_vse_slike by this.inject<Najdi_vse_slike>()
    val log by this.inject<LogService>()
    var zip_zvezek = Opazovan<Datoteka>()
    lateinit var zip_dir: File
    lateinit var zip_entries: List<ZipEntry>

    @FXML
    fun initialize() {
        println("init Izberi_zip_zvezek")
        this.LIST.self.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            this.init(datoteka = newValue)
        }

        this.LIST.init(this.resourseService.najdi_zip_datoteke())
    }

    private fun init(datoteka: Datoteka) {
        this.log.info("init: $datoteka")

        //Dobi zip entries.
        this.zip_entries = ZipFile(datoteka.file.absolutePath).entries().toList()

        //Posodobi tree table.
        this.zip_dir = Paths.get(datoteka.file.absolutePath, "../${datoteka.file.nameWithoutExtension}").normalize().toFile()

        val ustvarjeniFilesi = this.zip_dir.listFiles()!!.sortedBy { it.name.toInt() }.map { it.name.toInt() }
        this.zip_entries.forEach {
            val ime = it.name.split("_").last().split(".").first().toInt()
            val color = if (ustvarjeniFilesi.contains(ime)) "darkgreen" else "darkred"
            this.FLOW.dodaj(ime = ime.toString(), color = color)
        }


        //Shrani zip zvezek
        this.zip_zvezek.value = datoteka
    }

//    fun naslednja_slika(): BufferedImage {
//        Dobi zip datoteko
//        val datoteka = this.zip_zvezek.value
//
//        Poglej koliko datotek si ze obdelal.
//        val steviloOpravljenihDatotek = this.FLOW.imena.size
//
//        Dobi zip datoteko ki je naslednja za obdelavo.
//        val nasledjiZipEntry = entries[steviloOpravljenihDatotek]
//
//        Nalozi naslednji zip entry kot sliko
//        val inputStream = zipFile.getInputStream(nasledjiZipEntry)
//        val bufferedImage = ImageIO.read(inputStream)
//
//        Zapri zip file
//        zipFile.close()
//
//        return bufferedImage
//    }

}
