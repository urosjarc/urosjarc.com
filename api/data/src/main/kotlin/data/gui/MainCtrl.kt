package data.gui

import data.services.ResouceService
import data.use_cases.Najdi_vse_zip_slike
import javafx.collections.FXCollections.observableArrayList
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.TreeItemPropertyValueFactory
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

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

    @FXML
    private fun zip_files_clicked() {
        val zipFile = this.zip_files.selectionModel.selectedItem
        for(slika in this.najdi_vse_zip_slike.zdaj(zipFile)){
            println("${slika.width} ${slika.height}")
        }

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
