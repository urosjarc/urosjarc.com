package data.gui2.parts;

import data.domain.Datoteka
import javafx.fxml.FXML
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableColumn
import javafx.scene.control.TreeTableView
import javafx.scene.control.cell.TreeItemPropertyValueFactory
import org.koin.core.component.KoinComponent
import java.nio.file.Paths

class TreeTableView_Datoteka : KoinComponent {

    @FXML
    lateinit var imeTTC: TreeTableColumn<Datoteka, String>

    fun init(datoteka: Datoteka) {
        val zvezekDir = Paths.get(datoteka.file.absolutePath, "../${datoteka.file.nameWithoutExtension}").normalize().toFile()

        val root = TreeItem(Datoteka(zvezekDir))

        val cakalnica = mutableListOf<TreeItem<Datoteka>>(root)
        while (cakalnica.isNotEmpty()) {
            val pacient = cakalnica.removeAt(0)
            pacient.isExpanded = false

            val files = pacient.value.file.listFiles()

            if (files != null) {
                if (files.isEmpty()) continue
            } else continue

            for (file in files) {
                val otrok = TreeItem(Datoteka(file))
                pacient.children.add(otrok)
                if (file.isDirectory) cakalnica.add(otrok)
            }
        }

        root.children.sortBy { it.value.ime.padStart(5, '0') }

        root.isExpanded = true
        this.self.root = root
    }

    @FXML
    lateinit var self: TreeTableView<Datoteka>

    @FXML
    fun initialize() {
        println("init TreeTableView_Datoteka")
        this.imeTTC.cellValueFactory = TreeItemPropertyValueFactory(Datoteka::ime.name);
    }

    @FXML
    fun clicked() {
        println("clicked")
    }
}
