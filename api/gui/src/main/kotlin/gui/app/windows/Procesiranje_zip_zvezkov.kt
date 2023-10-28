package gui.app.windows

import core.extend.ime
import core.services.JsonService
import gui.app.widgets.BarveSlik
import gui.app.widgets.Izberi_zip_zvezek
import gui.app.widgets.Prikazi_log_dnevnik
import gui.app.widgets.Procesiranje_slike
import gui.base.App
import gui.extend.shrani
import gui.services.LogService
import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
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
    val json by this.inject<JsonService>()

    @FXML
    fun initialize() {
        this.IZBERI.izbrana_slika.opazuj { this.PROCES.init(stSlike = it.first, slika = it.second) }
        this.PROCES.POP.preskociSliko.opazuj { this.preskoci_popravljanje_trenutne_slike() }
        this.PROCES.ANO.potrdiB.setOnAction { this.potrditev_anotiranja_trenutne_slike() }
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

    fun preskoci_popravljanje_trenutne_slike() {
        this.ustvari_direktorij()
        this.IZBERI.FLOW.posodobi(ime = this.PROCES.stSlike.toString(), color = BarveSlik.PRESKOCENO.ime)
        this.IZBERI.izberi_sliko(this.PROCES.stSlike + 1)
    }

    fun potrditev_anotiranja_trenutne_slike() {
        this.ustvari_direktorij()
        this.shrani_sliko()
        this.shrani_stran()
        this.IZBERI.FLOW.posodobi(ime = this.PROCES.stSlike.toString(), color = BarveSlik.OPRAVLJENO.ime)
        this.IZBERI.izberi_sliko(this.PROCES.stSlike + 1)
    }

    fun ustvari_direktorij() {
        this.slika_dir().mkdir()
    }

    fun shrani_sliko() {
        val slika = this.PROCES.ANO.slika
        val imgFile = this.slika_dir("popravljanje.png")
        slika.shrani(imgFile)
    }

    fun shrani_stran() {
        val stran = this.PROCES.ANO.stran
        val stranFile = this.slika_dir("stran.json")
        stranFile.writeText(text = this.json.zakodiraj(value = stran))
    }

    fun slika_dir(ime: String = "") = File(this.IZBERI.zip_save_dir, "${this.PROCES.stSlike}/$ime")

}
