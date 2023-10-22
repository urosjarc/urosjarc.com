package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.base.Opazovan
import gui.domain.*
import gui.extend.dodajSpodaj
import gui.services.LogService
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
    private val log by this.inject<LogService>()
    private val anotiraj_omega_odsek by this.inject<Anotiraj_omega_odsek>()

    lateinit var dragStart: Vektor
    private var dragRectangle: Rectangle = Rectangle()

    private lateinit var stran: Stran
    var odseki = mutableListOf<Odsek>()
    val koncniOdseki = Opazovan<MutableList<Odsek>>()


    private var indexOdseka = 0
    private var indexDelOdseka = 0


    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
        this.DOWN_IMG.zoom.opazuj { this.redraw_down_img() }
        this.DOWN_IMG.self.setOnMousePressed { this.dragStart = Vektor(x = it.x, y = it.y) }
        this.DOWN_IMG.self.setOnMouseReleased { this.self_onMouseReleased() }
        this.DOWN_IMG.self.setOnMouseDragged { this.self_onMouseDragg(me = it) }

        this.naslednjiDelB.setOnAction { this.naslednjiDelOdseka(naprej = true) }
        this.prejsnjiDelB.setOnAction { this.naslednjiDelOdseka(naprej = false) }

        this.izbrisiCelotniDelB.setOnAction {
            println("Prden celoten del")
        }

        this.izbrisiDodaneAnotacijeB.setOnAction {
            this.delOdseka.anotacije = mutableListOf(this.delOdseka.anotacije.first())
            this.init_down_img()
            this.init_top_img()
        }

        this.izbrisiVseAnotacijeB.setOnAction {
            this.delOdseka.anotacije = mutableListOf()
            this.init_down_img()
            this.init_top_img()
        }
    }

    fun init(stran: Stran) {
        this.indexOdseka = 0
        this.stran = stran
        this.odseki = this.razrezi_stran.zdaj(stran = stran)
        this.init_odsek(naprej = true)
    }

    private fun init_odsek(naprej: Boolean) {
        if(this.odsek.deli.isEmpty()) this.anotiraj_omega_odsek.zdaj(odsek = this.odsek)
        this.indexDelOdseka = if (naprej) 0 else this.odsek.deli.size - 1

        this.init_down_img()
        this.init_top_img()
    }

    private fun init_down_img() {
        this.DOWN_IMG.init(this.odsek.slika.img, sirokaSlika = true)
    }

    private fun init_top_img() {
        val imgs = this.delOdseka.anotacije.map {
            try {
                this.odsek.slika.img.getSubimage(it.x.toInt(), it.y.toInt(), it.width.toInt(), it.height.toInt())
            } catch (err: Throwable){
                println("Anot: $it")
                val img = this.odsek.slika.img
                println("Img: ${img.width} ${img.height}")
                throw Throwable("asdf")
            }
        }.toMutableList()

        if(imgs.size == 0){
            println("asdfsdf")
        }

        var img = imgs.removeAt(0)
        imgs.forEach { img = img.dodajSpodaj(it) }

        this.TOP_IMG.init(img, sirokaSlika = true)
        this.redraw_down_img()
    }

    private fun redraw_down_img() {
        this.DOWN_IMG.pobrisiOzadje()
        this.delOdseka.anotacije.forEach { this.DOWN_IMG.narisi_rectangle(ano = it, color = Color.RED) }
    }

    private fun self_onMouseReleased() {
        val anotacija = this.DOWN_IMG.anotacijaKvadrata(r = this.dragRectangle, text = "", tip = Anotacija.Tip.NALOGA)
        this.delOdseka.anotacije.add(anotacija)

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

    private fun naslednjiOdsek(naprej: Boolean) {
        this.indexOdseka += if (naprej) 1 else -1

        if (this.indexOdseka >= this.odseki.size) {
            this.indexOdseka = this.odseki.size - 1
            this.zakljuci()
        } else if (this.indexOdseka < 0) {
            this.indexOdseka = 0
        }
        this.init_odsek(naprej = naprej)
    }

    private fun naslednjiDelOdseka(naprej: Boolean) {
        this.indexDelOdseka += if (naprej) 1 else -1

        if (this.indexDelOdseka >= this.odsek.deli.size) {
            this.naslednjiOdsek(naprej = true)
        } else if (this.indexDelOdseka < 0) {
            this.naslednjiOdsek(naprej = false)
        } else {
            this.init_top_img()
        }
    }

    private fun zakljuci() {
        this.koncniOdseki.value = this.odseki
    }

    val delOdseka get() = this.odsek.deli[this.indexDelOdseka]
    val odsek get() = this.odseki[this.indexOdseka]

}
