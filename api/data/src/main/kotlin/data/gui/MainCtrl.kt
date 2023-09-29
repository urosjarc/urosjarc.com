package data.gui

import data.services.ResouceService
import data.use_cases.Najdi_vse_zip_slike
import javafx.collections.FXCollections.observableArrayList
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.TreeItemPropertyValueFactory
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

data class Node(
    val name: String,
    val test: String,
)

class MainCtrl : KoinComponent {

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
    fun initialize() {
        this.zip_files_update()
        this.zvezki_update()
        this.info_update()
    }

    private fun zip_files_update() {
        val files = resouceService.najdi_zip_datoteke()
        this.zip_files.items = observableArrayList(files)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @FXML
    private fun zip_files_clicked() = GlobalScope.launch(Dispatchers.Main) { // launch coroutine in the main thread
        napredek.progress = 0.0

        val zipFile = zip_files.selectionModel.selectedItem
        val widths = mutableListOf<Int>()
        val heights = mutableListOf<Int>()

        for (zipSlika in najdi_vse_zip_slike.zdaj(zipFile)) {
            widths.add(zipSlika.img.width)
            heights.add(zipSlika.img.height)
            if (zipSlika.index % 5 == 0) {
                napredek.progress = zipSlika.index / zipSlika.size.toDouble()
                delay(1)
            }
        }

        val aveWidth = widths.average()
        val aveHeight = heights.average()

        val aveDiffWidth = widths.map { (it - aveWidth).absoluteValue }.average().roundToInt()
        val aveDiffHeight = heights.map { (it - aveHeight).absoluteValue }.average().roundToInt()

        info.text = """
            Width: ${aveWidth} +- ${aveDiffWidth} (${widths.min()}, ${widths.max()})
            Height: ${aveHeight} +- ${aveDiffHeight} (${heights.min()}, ${heights.max()})
        """.trimIndent()

        napredek.progress = 0.0
    }

    private fun zvezki_update() {
        val zvezkiRoot = TreeItem(Node("root", "root"))
        val child1 = TreeItem(Node("name2", "test2"))
        val child2 = TreeItem(Node("name3", "test3"))

        zvezkiRoot.isExpanded = true
        child1.children.setAll(child2)
        zvezkiRoot.children.setAll(child1)

        val fileNameCol: TreeTableColumn<Node, String> = TreeTableColumn("Name")
        val lastModifiedCol: TreeTableColumn<Node, String> = TreeTableColumn("Test")

        fileNameCol.cellValueFactory = TreeItemPropertyValueFactory("name");
        lastModifiedCol.cellValueFactory = TreeItemPropertyValueFactory("test");

        this.zvezki.columns.addAll(fileNameCol, lastModifiedCol)
        this.zvezki.root = zvezkiRoot
        this.zvezki.isEditable = true

    }

    private fun info_update() {

        this.info.text = "asdfasdfsadfsdf"

    }
}
