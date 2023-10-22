package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.base.Opazovan
import gui.domain.*
import gui.extend.dodajSpodaj
import gui.use_cases.Anotiraj_omego_nalogo
import gui.use_cases.Razrezi_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


abstract class Rezanje_slike_Ui : KoinComponent {
    @FXML
    lateinit var top_imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var down_imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var prejsnjiDelB: Button

    @FXML
    lateinit var naslednjiDelB: Button

    @FXML
    lateinit var izbrisiDodaneAnotacijeB: MenuItem

    @FXML
    lateinit var izbrisiVseAnotacijeB: MenuItem

    @FXML
    lateinit var izbrisiCelotniDelB: MenuItem

    @FXML
    lateinit var ustvariDelB: Button

    val TOP_IMG get() = this.top_imageView_bufferedImage_Controller
    val DOWN_IMG get() = this.down_imageView_bufferedImage_Controller
}

class Rezanje_slike : Rezanje_slike_Ui() {
    private val razrezi_stran by this.inject<Razrezi_stran>()
    private val anotiraj_omego_nalogo by this.inject<Anotiraj_omego_nalogo>()

    private lateinit var stran: Stran
    lateinit var naloga: Naloga
    lateinit var dragStart: Vektor
    var dragRectangle: Rectangle = Rectangle()

    private var odseki = listOf<Odsek>()
    private var nalogeOdsekov = mutableMapOf<Odsek, Naloga>()

    private var indexOdseka = 0
    private var indexDelNaloge = 0

    var koncneNaloge = Opazovan<List<Naloga>>()

    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
        this.DOWN_IMG.zoom.opazuj { this.redraw_down_img() }
        this.DOWN_IMG.self.setOnMousePressed { this.dragStart = Vektor(x = it.x, y = it.y) }
        this.DOWN_IMG.self.setOnMouseReleased { this.self_onMouseReleased(me = it) }
        this.DOWN_IMG.self.setOnMouseDragged { this.self_onMouseDragg(me = it) }

        this.naslednjiDelB.setOnAction { this.naslednjiDelNaloge(naprej = true) }
        this.prejsnjiDelB.setOnAction { this.naslednjiDelNaloge(naprej = false) }

        this.izbrisiCelotniDelB.setOnAction {
            println("Prden celoten del")
        }

        this.izbrisiDodaneAnotacijeB.setOnAction {
            this.delNaloge.anotacije = mutableListOf(this.delNaloge.anotacije.first())
            this.init_down_img()
            this.init_top_img()
        }

        this.izbrisiVseAnotacijeB.setOnAction {
            this.delNaloge.anotacije = mutableListOf()
            this.init_down_img()
            this.init_top_img()
        }
    }

    fun init(stran: Stran) {
        this.indexOdseka = 0
        this.stran = stran
        this.odseki = this.razrezi_stran.zdaj(stran = stran)
        this.init_naloga(naprej = true)
    }

    private fun init_naloga(naprej: Boolean) {
        this.naloga = this.nalogeOdsekov.getOrDefault(this.odsek, defaultValue = this.anotiraj_omego_nalogo.zdaj(odsek = this.odsek))
        this.nalogeOdsekov.putIfAbsent(this.odsek, this.naloga)

        this.indexDelNaloge = if (naprej) 0 else this.naloga.deli.size - 1

        this.init_down_img()
        this.init_top_img()
    }

    private fun init_down_img() {
        this.DOWN_IMG.init(this.odsek.slika.img, sirokaSlika = true)
    }

    private fun init_top_img() {
        val imgs = this.delNaloge.anotacije.map {
            this.odsek.slika.img.getSubimage(it.x.toInt(), it.y.toInt(), it.width.toInt(), it.height.toInt())
        }.toMutableList()

        var img = imgs.removeAt(0)
        imgs.forEach { img = img.dodajSpodaj(it) }

        this.TOP_IMG.init(img, sirokaSlika = true)
        this.redraw_down_img()
    }

    private fun redraw_down_img() {
        this.DOWN_IMG.pobrisiOzadje()
        this.delNaloge.anotacije.forEach { this.DOWN_IMG.narisi_rectangle(ano = it, color = Color.RED) }
    }

    private fun self_onMouseReleased(me: MouseEvent) {
        val anotacija = this.DOWN_IMG.anotacijaKvadrata(r = this.dragRectangle, text = "", tip = Anotacija.Tip.NALOGA)
        naloga.deli[this.indexDelNaloge].anotacije.add(anotacija)

        this.redraw_down_img()
        this.init_top_img()
    }

    private fun self_onMouseDragg(me: MouseEvent) {
        //Odstrani drag rectangle
        this.DOWN_IMG.backgroundP.children.remove(this.dragRectangle)

        //Ustvari drag rectangle
        val v = Vektor(x = me.x, y = me.y)
        this.dragRectangle = Rectangle(
            this.dragStart.x,
            this.dragStart.y,
            v.x - this.dragStart.x,
            v.y - this.dragStart.y
        ).apply {
            this.fill = null
            this.stroke = Color.MAGENTA
            this.strokeWidth = 2.0
        }

        //Dodaj drag rectangle v background
        this.DOWN_IMG.backgroundP.children.add(this.dragRectangle)
    }

    private fun naslednjaNaloga(naprej: Boolean) {
        this.indexOdseka += if (naprej) 1 else -1

        if (this.indexOdseka >= this.odseki.size) {
            this.indexOdseka = this.odseki.size - 1
            this.zakljuci()
        } else if (this.indexOdseka < 0) {
            this.indexOdseka = 0
        }
        this.init_naloga(naprej = naprej)
    }

    private fun naslednjiDelNaloge(naprej: Boolean) {
        this.indexDelNaloge += if (naprej) 1 else -1

        if (this.indexDelNaloge >= naloga.deli.size) {
            this.naslednjaNaloga(naprej = true)
        } else if (this.indexDelNaloge < 0) {
            this.naslednjaNaloga(naprej = false)
        } else {
            this.init_top_img()
        }
    }

    private fun zakljuci() {
        this.koncneNaloge.value = this.nalogeOdsekov.values.toList()
    }

    val delNaloge get() = this.naloga.deli[this.indexDelNaloge]
    val odsek get() = this.odseki[this.indexOdseka]

}
