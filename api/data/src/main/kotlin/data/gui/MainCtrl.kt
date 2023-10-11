package data.gui

import data.domain.Anotacija
import data.domain.AnotacijeStrani
import data.domain.ZipSlika
import data.extend.*
import data.services.OcrService
import data.services.ResouceService
import data.use_cases.Najdi_vse_zip_slike
import data.use_cases.Procesiraj_omego_sliko
import javafx.collections.FXCollections.observableArrayList
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.TreeItemPropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
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
    /**
     * Const
     */
    companion object {
        val TMP_SLIKA = "tmp.png"
        val ORIGINAL_SLIKA = "original.png"
        val POPRAVLJENA_SLIKA = "popravljena.png"
        val OCR_ANNOTACIJE = "ocr.json"
        val OCR_FINAL_ANNOTACIJE = "ocr_final.json"
        val OCR_SLIKA = "ocr.png"
    }

    /**
     * Services
     */
    val resouceService: ResouceService by this.inject()
    val ocrService: OcrService by this.inject()
    val json: Json by this.inject()

    /**
     * Usecases
     */
    val najdi_vse_zip_slike: Najdi_vse_zip_slike by this.inject()
    val procesirajSliko: Procesiraj_omego_sliko by this.inject()

    /**
     * State
     */
    val zip_slike = mutableListOf<ZipSlika>()
    var trenutnaSlika = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
    var popravljenaSlika = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
    var ocrSlika = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)

    /**
     * leva stran
     */
    @FXML
    lateinit var zip_files: ListView<File>

    @FXML
    lateinit var napredek: ProgressBar

    @FXML
    lateinit var zacni: Button

    @FXML
    lateinit var zvezki: TreeTableView<Node>

    @FXML
    lateinit var info: ListView<String>


    /**
     * Desna stran
     */
    @FXML
    lateinit var logs: ListView<String>

    @FXML
    lateinit var prekini: Button


    @FXML
    lateinit var slikaOcr: ImageView

    val slikaOcrMenu: ContextMenu = ContextMenu()


    /**
     * Sredina
     */
    @FXML
    lateinit var tabs: TabPane

    /**
     * Controls for obdelano sliko
     */
    @FXML
    lateinit var obdelava: Tab

    @FXML
    lateinit var slika: ImageView

    @FXML
    lateinit var obdelavaLabel: Label

    @FXML
    lateinit var rotacija: Slider

    @FXML
    lateinit var margin: Slider

    @FXML
    lateinit var resetiraj: Button


    @FXML
    lateinit var potrdi: Button


    /**
     * Plosca z slikami
     */

    @FXML
    lateinit var prepoznava: Tab

    var ocrDragEvents = mutableListOf<MouseEvent>()


    private fun alert(msg: String) {
        this.logs.items.add("!!! $msg\n")
    }

    private fun info(msg: String) {
        this.logs.items.add("... $msg\n")
    }

    private fun trenutniIndex(): Int {
        val lastNode = this.zvezki.root.children.lastOrNull()
        return lastNode?.value?.name?.toInt() ?: 0
    }

    private fun trenutniDir(name: String = "", di: Int = 0): File {
        val node = this.zvezki.root.value
        val i = this.trenutniIndex()
        val file = File(node.file, "${i + di}")
        return File(file, name)
    }

    @FXML
    fun resetiraj_clicked() {
        val file = this.trenutniDir(TMP_SLIKA)
        val deskew = this.trenutnaSlika.deskew()
        val m = margin.value.toInt()
        val img = deskew.second.getSubimage(m, m, deskew.second.width - 2 * m, deskew.second.height - 2 * m).negative()

        img.drawGrid()
        img.save(file)

        slika.image = Image(file.inputStream())
        this.rotacija.value = deskew.first
    }

    @FXML
    fun initialize() {
        /**
         * Fill tree
         */
        val col_file: TreeTableColumn<Node, String> = TreeTableColumn("File")
        col_file.cellValueFactory = TreeItemPropertyValueFactory("name");
        this.zvezki.columns.addAll(col_file)

        this.slikaOcr.setOnMouseDragged { this.slikaOcr_clicked(event = it) }

        /**
         * Fill zvezki
         */
        val files = resouceService.najdi_zip_datoteke()
        this.zip_files.items = observableArrayList(files)

        /**
         * On rotacija in margin change
         */
        fun update() {
            val r = "%.2f°".format(this.rotacija.value)
            val m = "%.2fpx".format(this.margin.value)
            obdelavaLabel.text = "Rotacija: $r, Margin: $m"
        }
        this.rotacija.valueProperty().addListener { _, _, _ -> update() }
        this.margin.valueProperty().addListener { _, _, _ -> update() }

        /**
         * Context menu for ocr vindow
         */
        Anotacija.Tip.values().forEach {
            val menuItem1 = MenuItem(it.name)
            this.slikaOcrMenu.items.add(menuItem1)
        }
        this.slikaOcr.setOnContextMenuRequested {
            this.slikaOcrMenu.show(this.slikaOcr, it.screenX, it.screenY)
        }
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
        opravljeno()
    }

    @FXML
    private fun prekini_clicked() {
        info("prekini")

    }

    @FXML
    private fun margin_done() {
        prepare_img()
    }

    @FXML
    private fun rotacija_done() {
        prepare_img()
    }

    private fun prepare_img() {
        val file = this.trenutniDir(TMP_SLIKA)
        var img = ImageHelper.rotateImage(this.trenutnaSlika, rotacija.value)
        val m = margin.value.toInt()
        img = img.getSubimage(m, m, img.width - 2 * m, img.height - 2 * m).negative()
        img.drawGrid()
        img.save(file)
        slika.image = Image(file.inputStream())
    }

    private fun opravljeno() {
        val index = this.trenutniIndex()
        val dir = this.trenutniDir(di = 1)
        val node = Node(dir)

        if (dir.mkdir()) {
            info("Stran ${dir.name} se je ustvarila.")
        }

        val zipSlika = zip_slike[if (index > 0) index - 1 else 0]
        val originalFile = File(dir, ORIGINAL_SLIKA)
        val tmpFile = File(dir, TMP_SLIKA)

        this.trenutnaSlika = zipSlika.img
        this.trenutnaSlika.save(originalFile)

        val deskew = zipSlika.img.deskew()
        val img = deskew.second.negative()
        img.drawGrid()
        img.save(tmpFile)

        slika.image = Image(tmpFile.inputStream())

        this.rotacija.value = deskew.first
        this.zvezki.root.children.add(TreeItem(node))
        this.zvezki.root = this.zvezki.root
    }

    @OptIn(ExperimentalSerializationApi::class)
    @FXML
    private fun potrdi_clicked() = GlobalScope.launch(Dispatchers.Main) {

        var img = ImageHelper.rotateImage(trenutnaSlika, rotacija.value)
        val m = margin.value.toInt()
        img = img.getSubimage(m, m, img.width - 2 * m, img.height - 2 * m)

        /**
         * Shrani popravljeno sliko
         */
        val popravljenaFile = trenutniDir(POPRAVLJENA_SLIKA)
        img.save(popravljenaFile)

        /**
         * Ustvari ocr json in shrani
         */
        val annoFile = trenutniDir(OCR_ANNOTACIJE)
        var annos: List<Anotacija>
        annos = ocrService.google(image = img).toMutableList()
        info("Ocr je prepoznal ${annos.size} elementov")
        annoFile.writeText(json.encodeToString(annos))
        info("Ocr annotation so se shranile v $annoFile")

        /**
         * Ustvari ocr sliko
         */
        val finalAnnoFile = trenutniDir(OCR_FINAL_ANNOTACIJE)
        val ocrSlikaFile = trenutniDir(OCR_SLIKA)
        val slikaAnno = procesirajSliko.zdaj(img = img, annos = annos)
        finalAnnoFile.writeText(json.encodeToString(slikaAnno))
        img.drawSlikaAnnotations(slikaAnno)
        img.save(ocrSlikaFile)
        slikaOcr.image = Image(ocrSlikaFile.inputStream())

        /**
         * Pripravi gui
         */
        tabs.selectionModel.select(prepoznava)
    }

    fun slikaOcr_draged(event: MouseEvent) {
        this.ocrDragEvents.add(event)
    }




        @OptIn(ExperimentalSerializationApi::class)
    fun slikaOcr_clicked(event: MouseEvent) {
        val popravljenaFile = trenutniDir(POPRAVLJENA_SLIKA)
        val img: BufferedImage = ImageIO.read(popravljenaFile)

        val r = img.height.toDouble() / img.width
        val h = this.slikaOcr.fitHeight
        val w = h / r

        val xPercent: Double = event.x / w
        val yPercent: Double = event.y / h
        val xReal = (xPercent * img.width).toInt()
        val yReal = (yPercent * img.height).toInt()

        val annos = json.decodeFromStream<List<Anotacija>>(trenutniDir(OCR_ANNOTACIJE).inputStream())
        val annosFinal = json.decodeFromStream<AnotacijeStrani>(trenutniDir(OCR_FINAL_ANNOTACIJE).inputStream())

        annos.forEach {
            if (it.contains(x = xReal, y = yReal)) {
                it.tip = Anotacija.Tip.NEZNANO
                annosFinal.glava.add(it)
            }
        }
        val ocrSlikaFile = trenutniDir(OCR_SLIKA)
        img.drawSlikaAnnotations(annosFinal)
        img.save(ocrSlikaFile)
        slikaOcr.image = Image(ocrSlikaFile.inputStream())
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
        val aveWidth = widths.average().toInt()
        val aveHeight = heights.average().toInt()
        val aveDiffWidth = widths.map { (it - aveWidth).absoluteValue }.average().roundToInt()
        val aveDiffHeight = heights.map { (it - aveHeight).absoluteValue }.average().roundToInt()

        /**
         * Allready parsed tree
         */
        info.items.add("Size: ${zip_slike.size}")
        info.items.add("Width: ${aveWidth} +- ${aveDiffWidth} (${widths.min()}, ${widths.max()})")
        info.items.add("Height: ${aveHeight} +- ${aveDiffHeight} (${heights.min()}, ${heights.max()})")
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
