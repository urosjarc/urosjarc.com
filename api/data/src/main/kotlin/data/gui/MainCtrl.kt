package data.gui

import data.domain.ZipSlika
import data.extend.deskew
import data.extend.save
import data.services.ResouceService
import data.use_cases.Najdi_vse_zip_slike
import javafx.collections.FXCollections.observableArrayList
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.TreeItemPropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import kotlinx.coroutines.*
import net.sourceforge.tess4j.util.ImageHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Paths
import javax.imageio.ImageIO
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


data class Node(
    val file: File,
) {
    val name: String get() = this.file.name
}

@OptIn(DelicateCoroutinesApi::class)
class MainCtrl : KoinComponent {

    var trenutnaSlika = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
    val zip_slike = mutableListOf<ZipSlika>()
    val resouceService: ResouceService by this.inject()
    val najdi_vse_zip_slike: Najdi_vse_zip_slike by this.inject()

    @FXML
    lateinit var zip_files: ListView<File>

    @FXML
    lateinit var napredek: ProgressBar

    @FXML
    lateinit var zvezki: TreeTableView<Node>

    @FXML
    lateinit var info: TextArea

    @FXML
    lateinit var zacni: Button

    @FXML
    lateinit var prekini: Button

    @FXML
    lateinit var logs: TextArea

    @FXML
    lateinit var slika: ImageView

    @FXML
    lateinit var potrdi: Button

    @FXML
    lateinit var rotacija: Slider

    @FXML
    fun initialize() {
        /**
         * Fill tree
         */
        val fileCol: TreeTableColumn<Node, String> = TreeTableColumn("File")
        fileCol.cellValueFactory = TreeItemPropertyValueFactory("name");
        this.zvezki.columns.addAll(fileCol)

        /**
         * Fill zvezki
         */
        val files = resouceService.najdi_zip_datoteke()
        this.zip_files.items = observableArrayList(files)

    }

    @FXML
    private fun zip_files_clicked() = GlobalScope.launch(Dispatchers.Main) { // launch coroutine in the main thread
        napredek.progress = 0.0

        /**
         * Getting zip file
         */
        val zipFile = zip_files.selectionModel.selectedItem

        /**
         * Getting images
         */
        zip_slike.clear()
        for (zipSlika in najdi_vse_zip_slike.zdaj(zipFile)) {
            zip_slike.add(zipSlika)
            if (zipSlika.index % 5 == 0) {
                napredek.progress = zipSlika.index / zipSlika.size.toDouble()
                delay(1)
            }
        }

        /**
         * Display results
         */
        info_update()
        zvezki_update(zipFile)


        napredek.progress = 0.0
        zacni.isDisable = false
        prekini.isDisable = false
    }

    private fun alert(msg: String) {
        this.logs.text += "!!! $msg\n"
    }

    private fun info(msg: String) {
        this.logs.text += "... $msg\n"
    }

    @FXML
    private fun zacni_clicked() = GlobalScope.launch(Dispatchers.Main) {
        val root = zvezki.root

        /**
         * Ustvarjanje osnovnega direktorija
         */
        if (root.value == null) {
            return@launch alert("Root je prazen.")
        } else if (!root.value.file.exists()) {
            info("Zvezek dir ne obstaja.")
            if (root.value.file.mkdir()) {
                info("Zvezek dir se je ustvaril.")
            } else {
                return@launch alert("Zvezek dir se ni ustvaril!")
            }
        } else {
            info("Zvezek dir obstaja.")
        }

        info("Struktura je pripravljena na parsanje")
        potrdi_clicked()
    }

    @FXML
    private fun prekini_clicked() {
        info("prekini")

    }

    @FXML
    private fun rotacija_done() {
        val lastNode = this.zvezki.root.children.lastOrNull()
        val newFile = File(lastNode?.value?.file, "popravljena.png")
        val imgNew = ImageHelper.rotateImage(this.trenutnaSlika, rotacija.value) // rotateImage static method
        imgNew.save(newFile)
        slika.image = Image(newFile.inputStream())
    }

    @FXML
    private fun potrdi_clicked() {
        val rootNode = this.zvezki.root.value
        val lastNode = this.zvezki.root.children.lastOrNull()
        val index = ((lastNode?.value?.name?.toInt() ?: 0) + 1)
        val stranDir = File(rootNode.file, "$index")
        val newNode = Node(stranDir)

        if (stranDir.mkdir()) {
            info("Stran ${stranDir.name} se je ustvarila.")
        }

        val image = zip_slike[index - 1]
        val imageFile = File(stranDir, "original.png")
        val tmpFile = File(stranDir, "popravljena.png")

        this.trenutnaSlika = image.img
        this.trenutnaSlika.save(imageFile)

        val popravljena = image.img.deskew()
        popravljena.save(tmpFile)

        slika.image = Image(tmpFile.inputStream())

        this.zvezki.root.children.add(TreeItem(newNode))
        this.zvezki.root = this.zvezki.root
    }

    private fun info_update() {
        /**
         * Image statistics
         */
        val widths = mutableListOf<Int>()
        val heights = mutableListOf<Int>()
        zip_slike.forEach { zipSlika ->
            widths.add(zipSlika.img.width)
            heights.add(zipSlika.img.height)
        }
        val aveWidth = widths.average()
        val aveHeight = heights.average()
        val aveDiffWidth = widths.map { (it - aveWidth).absoluteValue }.average().roundToInt()
        val aveDiffHeight = heights.map { (it - aveHeight).absoluteValue }.average().roundToInt()

        /**
         * Allready parsed tree
         */


        info.text = """
            Size: ${zip_slike.size}
            Width: ${aveWidth} +- ${aveDiffWidth} (${widths.min()}, ${widths.max()})
            Height: ${aveHeight} +- ${aveDiffHeight} (${heights.min()}, ${heights.max()})
        """.trimIndent()
    }

    private fun zvezki_update(zipFile: File) {
        val zvezekDir = Paths.get(zipFile.absolutePath, "../${zipFile.nameWithoutExtension}").normalize().toFile()

        val root = TreeItem(Node(zvezekDir))

        val cakalnica = mutableListOf<TreeItem<Node>>(root)
        while (cakalnica.isNotEmpty()) {
            val pacient = cakalnica.removeAt(0)
            pacient.isExpanded = true

            val files = pacient.value.file.listFiles()

            if (files != null) {
                if (files.isEmpty()) continue
            } else continue

            for (file in files) {
                val otrok = TreeItem(Node(file))
                pacient.children.add(otrok)
                if (file.isDirectory) cakalnica.add(otrok)
            }
        }

        root.children.sortBy { it.value.name.padStart(5, '0') }

        this.zvezki.root = root

    }

}
