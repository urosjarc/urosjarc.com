package gui.app.windows

import core.extend.ime
import core.services.JsonService
import gui.app.widgets.BarveSlik
import gui.app.widgets.Izberi_zip_zvezek
import gui.app.widgets.Procesiranje_slike
import gui.base.App
import gui.domain.*
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
import javax.imageio.ImageIO

abstract class Procesiranje_zip_zvezkov_Ui : Application(), KoinComponent {
    @FXML
    lateinit var izberi_zip_zvezek_Controller: Izberi_zip_zvezek

    @FXML
    lateinit var procesiranje_slike_Controller: Procesiranje_slike

    val IZBERI get() = this.izberi_zip_zvezek_Controller
    val PROCES get() = this.procesiranje_slike_Controller
}

enum class Datoteke(val ime: String) {
    ODSEKI_JSON("odseki.json"),
    STRAN_JSON("stran.json"),
    POPRAVLJANJE_PNG("popravljanje.png"),
}

class Procesiranje_zip_zvezkov : Procesiranje_zip_zvezkov_Ui() {

    val log by this.inject<LogService>()
    val json by this.inject<JsonService>()

    @FXML
    fun initialize() {
        this.IZBERI.izbrana_slika.opazuj { this.izberi_trenutno_sliko(it.first, it.second) }
        this.PROCES.POP.preskociSliko.opazuj { this.preskoci_trenutno_sliko() }
        this.PROCES.REZ.potrdiB.setOnAction { this.shrani_procesiranje_trenutne_slike() }
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

    fun izberi_trenutno_sliko(stSlike: Int, slika: BufferedImage) {
        val dir = this.slika_dir(stSlike = stSlike)
        this.log.info("Direktorij trenutne slike: $dir")
        if (dir.exists() && dir.listFiles()!!.isNotEmpty()) {
            this.log.info("Direktorij ze obstaja!")
            val imgFile = this.slika_dir(Datoteke.POPRAVLJANJE_PNG, stSlike = stSlike)
            val stranFile = this.slika_dir(Datoteke.STRAN_JSON, stSlike = stSlike)
            val odsekiFile = this.slika_dir(Datoteke.ODSEKI_JSON, stSlike = stSlike)
            val stran = this.json.dekodiraj<Stran>(value = stranFile.readText())
            val odseki = this.json.dekodiraj<MutableList<Odsek>>(value = odsekiFile.readText())
            val img = ImageIO.read(imgFile)
            this.PROCES.init(stSlike = stSlike, slika = img, stran = stran, odseki=odseki)
        } else {
            this.log.info("Direktorij ne obstaja!")
            this.PROCES.init(stSlike = stSlike, slika = slika, stran = null, odseki=null)
        }
    }

    fun preskoci_trenutno_sliko() {
        this.log.info("Preskoci trenutno sliko")
        this.ustvari_direktorij()
        this.IZBERI.FLOW.posodobi(ime = this.PROCES.stSlike.toString(), color = BarveSlik.PRESKOCENO.ime)
        this.IZBERI.izberi_sliko(this.PROCES.stSlike + 1)
    }

    fun shrani_procesiranje_trenutne_slike() {
        this.ustvari_direktorij()

        //Shrani sliko
        val slika = this.PROCES.ANO.slika
        val imgFile = this.slika_dir(dat = Datoteke.POPRAVLJANJE_PNG)
        this.log.info("Shrani sliko: $imgFile")
        slika.shrani(imgFile)

        //Shrani stran
        val stran = this.PROCES.ANO.stran
        val stranFile = this.slika_dir(dat = Datoteke.STRAN_JSON)
        this.log.info("Shrani stran: $stranFile")
        stranFile.writeText(text = this.json.zakodiraj(value = stran))

        //Shrani odsek
        val odseki = this.PROCES.REZ.odseki
        val odsekiFile = this.slika_dir(dat = Datoteke.ODSEKI_JSON)
        this.log.info("Shrani odseke: $odsekiFile")
        odsekiFile.writeText(text = this.json.zakodiraj(value = odseki))

        //Posodobi flow
        this.IZBERI.FLOW.posodobi(ime = this.PROCES.stSlike.toString(), color = BarveSlik.OPRAVLJENO.ime)
        this.IZBERI.izberi_sliko(this.PROCES.stSlike + 1)
    }


    fun ustvari_direktorij() {
        this.log.info("Ustvari direktorij za trenutno sliko.")
        this.slika_dir().mkdirs()
    }
    fun slika_dir(dat: Datoteke? = null, stSlike: Int? = null) = File(this.IZBERI.zip_save_dir, "${stSlike ?: this.PROCES.stSlike}/${dat?.ime ?: ""}")

}
