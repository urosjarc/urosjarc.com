package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.base.Izbire
import gui.domain.Odsek
import gui.domain.Okvir
import gui.domain.Stran
import gui.domain.Vektor
import gui.extend.izrezi
import gui.use_cases.Anotiraj_omega_odsek
import gui.use_cases.Razrezi_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage


abstract class Rezanje_slike_Ui : KoinComponent {


    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var prejsnjiDelB: Button

    @FXML
    lateinit var naslednjiDelB: Button

    @FXML
    lateinit var ustvariDelB: Button

    @FXML
    lateinit var izbrisiDodaneAnotacijeB: MenuItem

    @FXML
    lateinit var izbrisiVseAnotacijeB: MenuItem

    @FXML
    lateinit var izbrisiCelotniDelB: MenuItem
    val IMG get() = this.imageView_bufferedImage_Controller
}

class Rezanje_slike : Rezanje_slike_Ui() {
    private lateinit var slika: BufferedImage
    private val anotiraj_omega_odsek by this.inject<Anotiraj_omega_odsek>()
    private val razrezi_stran by this.inject<Razrezi_stran>()

    private var dragRectangle: Rectangle = Rectangle()
    lateinit var dragStart: Vektor

    lateinit var stran: Stran
    private var userOkvirji = mutableListOf<Okvir>()
    var odseki = Izbire<Odsek>()
    var deliOdseka = Izbire<Odsek>()


    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
        this.IMG.zoom.opazuj { this.na_novo_narisi_anotacije_v_ozadju() }
        this.IMG.self.setOnMousePressed { this.dragStart = Vektor(x = it.x.toInt(), y = it.y.toInt()) }
        this.IMG.self.setOnMouseReleased { this.onMouseReleased() }
        this.IMG.self.setOnMouseDragged { this.onMouseDragg(me = it) }

        this.naslednjiDelB.setOnAction { this.pripravi_naslednji_del_odseka(naprej = true) }
        this.prejsnjiDelB.setOnAction { this.pripravi_naslednji_del_odseka(naprej = false) }

        this.izbrisiCelotniDelB.setOnAction { println("Prden celoten del") }
        this.izbrisiDodaneAnotacijeB.setOnAction { this.IMG.init(this.slika) }
        this.izbrisiVseAnotacijeB.setOnAction { this.IMG.init(this.slika) }
    }

    fun init(slika: BufferedImage, stran: Stran) {
        this.slika = slika
        this.stran = stran
        this.odseki.izbire = this.razrezi_stran.zdaj(stran = stran)
        this.anotiraj_trenutni_odsek(naprej = true)
    }


    private fun pripravi_naslednji_odsek(naprej: Boolean) {
        if (naprej) this.odseki.naprej() else this.odseki.nazaj()
        this.anotiraj_trenutni_odsek(naprej = naprej)
    }

    private fun pripravi_naslednji_del_odseka(naprej: Boolean) {
        if (naprej) this.deliOdseka.naprej() else this.deliOdseka.nazaj()
        this.na_novo_narisi_anotacije_v_ozadju()
    }

    private fun anotiraj_trenutni_odsek(naprej: Boolean) {
        this.deliOdseka.izbire = this.anotiraj_omega_odsek.zdaj(odsek = this.odseki.trenutni)
        this.deliOdseka.resetiraj(naKonec = !naprej)
        this.IMG.init(this.slika.izrezi(okvir = this.odseki.trenutni.okvir))
    }

    private fun na_novo_narisi_anotacije_v_ozadju() {
        this.IMG.pobrisiOzadje()
        this.userOkvirji.forEach { this.IMG.narisi_okvir(okvir = it, color = Color.RED) }
        this.IMG.narisi_okvir(okvir = this.deliOdseka.trenutni.okvir, color = Color.RED)
    }

    private fun onMouseReleased() {
        val okvir = this.IMG.vOkvir(r = this.dragRectangle)
        this.userOkvirji.add(okvir)
        this.na_novo_narisi_anotacije_v_ozadju()
    }

    private fun onMouseDragg(me: MouseEvent) {
        //Odstrani drag rectangle
        this.IMG.backgroundP.children.remove(this.dragRectangle)

        //Ustvari drag rectangle
        val v = Vektor(x = me.x.toInt(), y = me.y.toInt())
        this.dragRectangle = Rectangle(
            this.dragStart.x.toDouble(),
            this.dragStart.y.toDouble(),
            (v.x - this.dragStart.x).toDouble(),
            (v.y - this.dragStart.y).toDouble()
        ).apply {
            this.fill = null
            this.stroke = Color.MAGENTA
            this.strokeWidth = 2.0
        }

        //Dodaj drag rectangle v background
        this.IMG.backgroundP.children.add(this.dragRectangle)
    }
}
