package gui.app.windows

import core.extend.ime
import gui.app.widgets.Izberi_zip_zvezek
import gui.app.widgets.Prikazi_log_dnevnik
import gui.app.widgets.Procesiranje_slike
import gui.base.App
import gui.domain.Datoteka
import gui.domain.Slika
import gui.extend.inputStream
import gui.extend.shrani
import gui.services.LogService
import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.Stage
import jfxtras.styles.jmetro.JMetro
import jfxtras.styles.jmetro.Style
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

abstract class Procesiranje_zip_zvezkov_Ui : Application(), KoinComponent {
    @FXML
    lateinit var izberi_zip_zvezek_Controller: Izberi_zip_zvezek

    @FXML
    lateinit var procesiranje_slike_Controller: Procesiranje_slike

    @FXML
    lateinit var prikazi_log_dnevnik_Controller: Prikazi_log_dnevnik

    val IZBERI get() = this.izberi_zip_zvezek_Controller
    val PROCES get() = this.procesiranje_slike_Controller
    val LOG get() = this.prikazi_log_dnevnik_Controller
}

class Procesiranje_zip_zvezkov : Procesiranje_zip_zvezkov_Ui() {

    val log by this.inject<LogService>()

    @FXML
    fun initialize() {
        println("init Procesiranje_zip_zvezkov")
        this.IZBERI.zip_zvezek.opazuj { this.PROCES.init(this.IZBERI.naslednja_slika()) }
        this.PROCES.REZ.koncniOdseki.opazuj { this.potrdi_procesiranje_trenutne_slike() }
        this.PROCES.POP.preskociSliko.opazuj { this.preskoci_popravljanje_trenutne_slike(it) }
    }

    override fun start(stage: Stage) {
        App.pripravi_DI()
        val fxmlLoader = FXMLLoader(this.javaClass.getResource("${ime<Procesiranje_zip_zvezkov>()}.fxml"))
        val scene = Scene(fxmlLoader.load())
        val jMetro = JMetro(Style.DARK)
        jMetro.scene = scene
        stage.scene = scene
        stage.show()
    }

    fun potrdi_procesiranje_trenutne_slike() {
        val alert = Alert(
            Alert.AlertType.CONFIRMATION, "Želite shraniti vaše spremembe?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL
        ).apply { this.showAndWait() }
        if (alert.result == ButtonType.YES) this.ustvari_direktorij_trenutne_slike()
    }

    fun preskoci_popravljanje_trenutne_slike(slika: Slika) {
        val dat = this.ustvari_direktorij_trenutne_slike()
        val file = File(dat.file, "popravljanje.png")
        slika.img.shrani(file)
        this.zacni_procesiranje_naslednje_slike()
    }

    fun ustvari_direktorij_trenutne_slike(): Datoteka {
        val zadnjiFolder = this.IZBERI.TREE.self.root.children.last().value
        this.log.info("Zadnji folder: ${zadnjiFolder.file}")
        val naslednjiFolder = File(zadnjiFolder.file.parent, (zadnjiFolder.ime.toInt() + 1).toString())
        this.log.info("Naslednji folder: ${naslednjiFolder}")
        naslednjiFolder.mkdir()
        this.log.info("Direktorij se je ustvaril.")
        val datoteka = Datoteka(file = naslednjiFolder)
        this.IZBERI.TREE.dodajDatoteko(datoteka)
        return datoteka
    }

    fun zacni_procesiranje_naslednje_slike() {
        this.PROCES.init(this.IZBERI.naslednja_slika())
    }
}
