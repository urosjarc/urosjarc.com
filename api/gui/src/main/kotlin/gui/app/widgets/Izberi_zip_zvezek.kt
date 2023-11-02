package gui.app.widgets

import gui.app.elements.FlowPane_Datoteka
import gui.app.elements.ListView_Datoteka
import gui.base.Opazovan
import gui.services.LogService
import gui.services.ResouceService
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ProgressBar
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

    @FXML
    lateinit var razreziB: Button

    val LIST get() = this.listView_datoteka_Controller
    val FLOW get() = this.flowPane_datoteka_Controller

}

enum class BarveSlik(val ime: String) {
    OPRAVLJENO("darkgreen"),
    NEOPRAVLJENO("darkred"),
    PRESKOCENO("blue")
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
        this.LIST.self.selectionModel.selectedItemProperty().addListener { _, _, newValue -> this.init(file = newValue.file) }
        this.LIST.init(this.resourseService.najdi_zip_datoteke())
    }

    private fun init(file: File) {
        this.log.warn("Izbran zip zvezek: $file")
        //Dobi zip entries.
        this.zip_file = ZipFile(file.absolutePath)
        this.zip_entries = this.zip_file.entries().toList()
        this.zip_save_dir = Paths.get(file.absolutePath, "../${file.nameWithoutExtension}").normalize().toFile()

        this.zip_save_dir.mkdir()

        this.FLOW.reset()
        this.zip_entries.forEachIndexed { index, _ ->
            val dir = File(this.zip_save_dir, index.toString())
            val barva = if (!dir.exists()) BarveSlik.NEOPRAVLJENO
            else if (dir.listFiles()!!.isEmpty()) BarveSlik.PRESKOCENO
            else BarveSlik.OPRAVLJENO

            this.FLOW.dodaj(ime = index.toString(), barva = barva.ime, onAction = { izberi_sliko(num = index) })
        }
    }

    fun izberi_sliko(num: Int) {
        this.log.warn("Izberi sliko: $num")
        //Dobi zip datoteko, ki je naslednja za obdelavo.
        val nasledjiZipEntry = this.zip_entries.getOrNull(num) ?: return

        //Nalozi naslednji zip entry kot sliko
        val inputStream = this.zip_file.getInputStream(nasledjiZipEntry)
        val bufferedImage = ImageIO.read(inputStream)

        this.izbrana_slika.value = Pair(num, bufferedImage)
    }

}
