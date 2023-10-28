package gui.app.windows

import core.extend.ime
import core.services.JsonService
import gui.app.widgets.Izberi_zip_zvezek
import gui.app.widgets.Prikazi_log_dnevnik
import gui.app.widgets.Procesiranje_slike
import gui.base.App
import gui.domain.Datoteka
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
import java.awt.image.BufferedImage
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
        println("init Procesiranje_zip_zvezkov")
        this.IZBERI.izbrana_slika.opazuj { this.PROCES.init(stSlike = it.first, slika = it.second) }
        this.PROCES.POP.preskociSliko.opazuj { this.preskoci_popravljanje_trenutne_slike(it) }
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

    fun preskoci_popravljanje_trenutne_slike(slika: BufferedImage) {
//        val dat = this.ustvari_direktorij_trenutne_slike()
//        this.shrani_sliko(dat = dat, slika = slika)
        this.zacni_procesiranje_naslednje_slike()
    }

    fun potrditev_anotiranja_trenutne_slike() {
//        val dat = this.ustvari_direktorij_trenutne_slike()
        val slika = this.PROCES.ANO.slika
//        this.shrani_sliko(dat = dat, slika = slika)
//        this.shrani_stran(dat = dat)
        this.zacni_procesiranje_naslednje_slike()
    }

    fun ustvari_direktorij_trenutne_slike(): Datoteka {
        val zadnjaDatoteka = this.IZBERI.FLOW.zadnjaDatoteka
        this.log.info("Zadnji folder: ${zadnjaDatoteka.file}")
        val naslednjiFolder = File(zadnjaDatoteka.file.parent, (zadnjaDatoteka.ime.toInt() + 1).toString())
        this.log.info("Naslednji folder: ${naslednjiFolder}")
        naslednjiFolder.mkdir()
        this.log.info("Direktorij se je ustvaril.")
        val datoteka = Datoteka(file = naslednjiFolder)
        this.IZBERI.FLOW.dodajDatoteko(datoteka)
        return datoteka
        return File()
    }

    fun shrani_sliko(dat: Datoteka, slika: BufferedImage) {
        val imgFile = File(dat.file, "popravljanje.png")
        slika.shrani(imgFile)
    }

    fun shrani_stran(dat: Datoteka) {
        val stran = this.PROCES.ANO.stran
        val stranFile = File(dat.file, "stran.json")
        stranFile.writeText(text = this.json.zakodiraj(value = stran))
    }

    fun zacni_procesiranje_naslednje_slike() {
//        this.PROCES.init(this.IZBERI.naslednja_slika())
    }
}
