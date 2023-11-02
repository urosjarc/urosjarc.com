package gui.app.windows

import core.extend.ime
import core.services.JsonService
import gui.app.widgets.BarveSlik
import gui.app.widgets.Izberi_zip_zvezek
import gui.app.widgets.Procesiranje_slike
import gui.base.App
import gui.domain.Odsek
import gui.domain.Stran
import gui.extend.shrani
import gui.services.LogService
import gui.use_cases.Razrezi_stran
import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import jfxtras.styles.jmetro.JMetro
import jfxtras.styles.jmetro.Style
import kotlinx.coroutines.*
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
    STRAN_JSON("stran.json"),
    POPRAVLJANJE_PNG("popravljanje.png"),
    NALOGE("naloge")
}

class Procesiranje_zip_zvezkov : Procesiranje_zip_zvezkov_Ui() {

    val log by this.inject<LogService>()
    val json by this.inject<JsonService>()
    val razrezi_stran by this.inject<Razrezi_stran>()

    @FXML
    fun initialize() {
        this.IZBERI.izbrana_slika.opazuj { this.izberi_trenutno_sliko(it.first, it.second) }
        this.IZBERI.razreziB.setOnAction { this.razrezi_anotiran_zvezek() }
        this.PROCES.POP.preskociSliko.opazuj { this.preskoci_trenutno_sliko() }
        this.PROCES.ANO.potrdiB.setOnAction { this.shrani_procesiranje_trenutne_slike() }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun razrezi_anotiran_zvezek() {
        val self = this
        GlobalScope.launch(Dispatchers.Main) {

            val rootDir = self.IZBERI.zip_save_dir
            val files = rootDir.listFiles()?.sortedBy { it.name.toInt() } ?: return@launch
            for ((k, stranDir) in files.withIndex()) {

                if (stranDir.list()!!.isEmpty()) continue

                val node = self.IZBERI.FLOW.najdi(ime = "$k")
                val style = node?.style
                self.IZBERI.FLOW.posodobi(ime = "$k", color = "orange")

                delay(1)

                val imgFile = File(stranDir, Datoteke.POPRAVLJANJE_PNG.ime)
                val stranFile = File(stranDir, Datoteke.STRAN_JSON.ime)
                val nalogeDir = File(stranDir, Datoteke.NALOGE.ime)
                val stran = self.json.dekodiraj<Stran>(value = stranFile.readText())

                nalogeDir.deleteRecursively()
                nalogeDir.mkdir()

                for ((i, odsek) in self.razrezi_stran.zdaj(stran = stran).withIndex()) {

                    if (!listOf(Odsek.Tip.NALOGA, Odsek.Tip.GLAVA).contains(odsek.tip)) continue

                    val originalImg = withContext(Dispatchers.IO) { ImageIO.read(imgFile) }
                    val okvirOdseka = odsek.okvir
                    val odsekImg = originalImg.getSubimage(okvirOdseka.start.x, okvirOdseka.start.y, okvirOdseka.sirina, okvirOdseka.visina)
                    val nalogaDir = File(nalogeDir, "$i")
                    nalogaDir.mkdir()
                    odsekImg.shrani(File(nalogeDir, "$i.png"))


                    for ((j, pododsek) in odsek.pododseki.withIndex()) {

                        val okvirPododseka = pododsek.okvir
                        try {
                            val img = originalImg.getSubimage(
                                okvirPododseka.start.x,
                                okvirPododseka.start.y,
                                okvirPododseka.sirina,
                                okvirPododseka.visina
                            )
                            val pododsekImg = File(nalogaDir, "$j.png")
                            img.shrani(pododsekImg)
                        } catch (err: Throwable) {
                            println("\n\n${originalImg.width} ${originalImg.height} $err")
                            println("${odsek.okvir} ${odsek.okvir.sirina} ${odsek.okvir.visina}")
                            println("$i $j $okvirOdseka ${okvirOdseka.sirina} ${okvirOdseka.visina}")
                        }
                    }
                }

                node?.style = style
            }
        }
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
            val stran = this.json.dekodiraj<Stran>(value = stranFile.readText())
            val img = ImageIO.read(imgFile)
            this.PROCES.init(stSlike = stSlike, slika = img, stran = stran)
        } else {
            this.log.info("Direktorij ne obstaja!")
            this.PROCES.init(stSlike = stSlike, slika = slika, stran = null)
        }
    }

    fun preskoci_trenutno_sliko() {
        this.log.info("Preskoci trenutno sliko")
        this.ustvari_direktorij()
        this.IZBERI.FLOW.posodobi(ime = this.PROCES.stSlike.toString(), color = BarveSlik.PRESKOCENO.ime)
        this.IZBERI.izberi_sliko(this.PROCES.stSlike + 1)
    }

    fun shrani_procesiranje_trenutne_slike() {
        this.log.info("Shrani procesiranje trenutne slike")
        this.ustvari_direktorij()
        this.shrani_sliko()
        this.shrani_stran()
        this.IZBERI.FLOW.posodobi(ime = this.PROCES.stSlike.toString(), color = BarveSlik.OPRAVLJENO.ime)
        this.IZBERI.izberi_sliko(this.PROCES.stSlike + 1)
    }

    fun ustvari_direktorij() {
        this.log.info("Ustvari direktorij za trenutno sliko.")
        this.slika_dir().mkdirs()
    }

    fun shrani_sliko() {
        val slika = this.PROCES.ANO.slika
        val imgFile = this.slika_dir(dat = Datoteke.POPRAVLJANJE_PNG)
        slika.shrani(imgFile)
        this.log.info("Shrani sliko: $imgFile")
    }

    fun shrani_stran() {
        val stran = this.PROCES.ANO.stran
        val stranFile = this.slika_dir(dat = Datoteke.STRAN_JSON)
        stranFile.writeText(text = this.json.zakodiraj(value = stran))
        this.log.info("Shrani stran: $stranFile")
    }

    fun slika_dir(dat: Datoteke? = null, stSlike: Int? = null) = File(this.IZBERI.zip_save_dir, "${stSlike ?: this.PROCES.stSlike}/${dat?.ime ?: ""}")

}
