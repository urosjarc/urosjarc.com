package gui.app.windows

import core.extend.ime
import core.services.JsonService
import gui.app.widgets.BarveSlik
import gui.app.widgets.Izberi_zip_zvezek
import gui.app.widgets.Procesiranje_slike
import gui.base.App
import gui.domain.*
import gui.extend.boundBox
import gui.extend.izrezi
import gui.extend.odstrani_prazen_prostor
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
            val zip_save_dir = self.IZBERI.zip_save_dir
            val zvezek = ZipZvezek(zip_save_dir.name)
            val strani_dirs = zip_save_dir.listFiles()?.sortedBy { it.name.toInt() } ?: listOf()
            var zadnjiOdsek: Odsek? = null

            //Sparsaj
            for (stran_dir in strani_dirs) {

                //Preskoci prazne strani
                if (stran_dir.list()!!.isEmpty()) continue

                //Dobi trenutno število strani
                val st_strani = stran_dir.name

                //Pobarvaj gumb ki se procesira
                val node = self.IZBERI.FLOW.najdi(ime = st_strani)
                val style = node?.style
                self.IZBERI.FLOW.posodobi(ime = st_strani, color = "orange")
                delay(1)

                //Nalozi sliko in json strani
                val stranImg = withContext(Dispatchers.IO) {
                    val imgFile = File(stran_dir, Datoteke.POPRAVLJANJE_PNG.ime)
                    ImageIO.read(imgFile)
                }
                val stran = self.json.dekodiraj<Stran>(value = File(stran_dir, Datoteke.STRAN_JSON.ime).readText())

                for (odsek in self.razrezi_stran.zdaj(stran = stran)) {

                    when (odsek.tip) {
                        Odsek.Tip.PODNALOGA -> throw Throwable("Fail!")
                        Odsek.Tip.NEZNANO -> throw Throwable("Fail!")
                        Odsek.Tip.TEORIJA -> {
                            zvezek.tematike.last().teorije.add(ZipTeorija(img = stranImg.izrezi(odsek.okvir)))
                        }

                        Odsek.Tip.NASLOV -> {
                            val imeTematike = odsek.tekst.split(" ").filterIndexed { index, s -> index != 0 }.joinToString(" ")
                            zvezek.tematike.add(ZipTematika(ime = imeTematike))
                        }

                        Odsek.Tip.NALOGA -> {
                            val nalogaImg = stranImg.izrezi(odsek.okvir).odstrani_prazen_prostor()
                            val zipNaloga = ZipNaloga(text = odsek.tekst, img = nalogaImg)
                            odsek.pododseki.map { it.okvir }.forEach {
                                val podnalogaImg = stranImg.getSubimage(it.start.x, it.start.y, it.sirina, it.visina).odstrani_prazen_prostor()
                                zipNaloga.podnaloge.add(ZipPodnaloga(img = podnalogaImg))
                            }; zvezek.tematike.last().naloge.add(zipNaloga)
                        }

                        Odsek.Tip.GLAVA -> {
                            when (zadnjiOdsek?.tip) {
                                Odsek.Tip.NALOGA -> {
                                    val zadnjaNaloga = zvezek.tematike.last().naloge.last()
                                    odsek.pododseki.map { it.okvir }.forEach {
                                        val img = stranImg.getSubimage(it.start.x, it.start.y, it.sirina, it.visina).odstrani_prazen_prostor()
                                        zadnjaNaloga.podnaloge.add(ZipPodnaloga(img = img))
                                    }
                                }

                                Odsek.Tip.TEORIJA -> {
                                    val zadnjaTematika = zvezek.tematike.last()
                                    odsek.pododseki.map { it.okvir }.forEach {
                                        val img = stranImg.getSubimage(it.start.x, it.start.y, it.sirina, it.visina).odstrani_prazen_prostor()
                                        zadnjaTematika.teorije.add(ZipTeorija(img = img))
                                    }
                                }

                                else -> throw Throwable("Fail!")
                            }
                        }
                    }

                    zadnjiOdsek = odsek
                }

                node?.style = style
            }

            //Shrani
            val zvezek_dir = File(self.IZBERI.zip_save_dir, "../${zvezek.ime}_db").also { it.deleteRecursively(); it.mkdir() }
            val img_dir = File(self.IZBERI.zip_save_dir, "../${zvezek.ime}_img").also { it.deleteRecursively(); it.mkdir() }
            var img_num = 0
            zvezek.tematike.forEachIndexed { st_tematike, tematika ->
                val tematika_dir = File(zvezek_dir, "$st_tematike. ${tematika.ime}").also { it.mkdir() }
                tematika.teorije.forEachIndexed { st_teorije, teorija ->
                    val teorija_dir = File(tematika_dir, "teorija").also { it.mkdir() }
                    teorija.img.shrani(File(teorija_dir, "teorija_$st_teorije.png"))
                    teorija.img.shrani(File(img_dir, "${(++img_num).toString().padStart(4, '0')}_TEORIJA.png"))
                }
                tematika.naloge.forEachIndexed { st_naloge, naloga ->
                    val naloga_dir = File(tematika_dir, "$st_naloge").also { it.mkdir() }
                    naloga.img.shrani(File(naloga_dir, "naloga.png"))
                    naloga.img.shrani(File(img_dir, "${(++img_num).toString().padStart(4, '0')}_NALOGA.png"))
                    File(naloga_dir, "besedilo.txt").writeText(naloga.text)
                    naloga.podnaloge.forEachIndexed { st_podnaloge, podnaloga ->
                        podnaloga.img.shrani(File(naloga_dir, "podnaloga_${st_podnaloge}.png"))
                        podnaloga.img.shrani(File(img_dir, "${(++img_num).toString().padStart(4, '0')}_PODNALOGA.png"))
                    }
                }
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
