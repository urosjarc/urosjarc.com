package gui.app.widgets

import gui.app.elements.FlowPane_Datoteka
import gui.app.elements.ListView_Datoteka
import gui.base.Opazovan
import gui.services.LogService
import gui.services.ResouceService
import javafx.fxml.FXML
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.imageio.ImageIO

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
    val log by this.inject<LogService>()

    lateinit var zip_save_dir: File
    lateinit var zip_file: ZipFile
    lateinit var zip_entries: List<ZipEntry>

    val izbrana_slika = Opazovan<Pair<Int, BufferedImage>>()

    @FXML
    fun initialize() {
        println("init Izberi_zip_zvezek")
        this.LIST.self.selectionModel.selectedItemProperty().addListener { _, _, newValue -> this.init(file = newValue.file) }
        this.LIST.init(this.resourseService.najdi_zip_datoteke())
    }

    private fun init(file: File) {
        this.log.info("init: $file")

        //Dobi zip entries.
        this.zip_file = ZipFile(file.absolutePath)
        this.zip_entries = this.zip_file.entries().toList()
        this.zip_save_dir = Paths.get(file.absolutePath, "../${file.nameWithoutExtension}").normalize().toFile()

        val ustvarjeniFilesi = this.zip_save_dir.listFiles()!!.sortedBy { it.name.toInt() }.map { it.name.toInt() }

        this.zip_entries.forEach {
            val ime = it.name.split("_").last().split(".").first().toInt()
            val color = if (ustvarjeniFilesi.contains(ime)) "darkgreen" else "darkred"
            this.FLOW.dodaj(ime = ime.toString(), color = color, onAction = { name -> izberi_sliko(num = name.toInt()) })
        }
    }

    fun izberi_sliko(num: Int) {
        //Dobi zip datoteko ki je naslednja za obdelavo.
        val nasledjiZipEntry = zip_entries[num]

        //Nalozi naslednji zip entry kot sliko
        val inputStream = this.zip_file.getInputStream(nasledjiZipEntry)
        val bufferedImage = ImageIO.read(inputStream)

        //Zapri zip file
        this.zip_file.close()

        this.izbrana_slika.value = Pair(num, bufferedImage)
    }
}
