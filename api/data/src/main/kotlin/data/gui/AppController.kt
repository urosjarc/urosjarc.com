package data.gui

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.TreeItemPropertyValueFactory

data class Node(
    val name: String,
    val test: String,
)

class AppController {

    @FXML
    private lateinit var zip_files: ListView<String>

    @FXML
    private lateinit var zvezki: TreeTableView<Node>

    @FXML
    private lateinit var info: TextArea

    @FXML
    fun initialize() {
        println("second")
        this.zip_files_update()
        this.zvezki_update()
        this.info_update()
    }

    private fun zip_files_update() {
        val names = FXCollections.observableArrayList(
            "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise"
        )
        this.zip_files.items = names
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
