package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.domain.*
import gui.extend.dodajSpodaj
import gui.use_cases.Anotiraj_omego_nalogo
import gui.use_cases.Razrezi_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
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
    lateinit var prejsnjaNalogaB: Button

    @FXML
    lateinit var resetirajNalogoB: Button

    @FXML
    lateinit var naslednjaNalogaB: Button

    @FXML
    lateinit var prejsnjiDelB: Button

    @FXML
    lateinit var naslednjiDelB: Button

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
    private var indexOdseka = 0
    private var indexDelOdseka = 0


    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
        this.DOWN_IMG.zoom.opazuj { this.redraw_down_img() }
        this.DOWN_IMG.self.setOnMousePressed { this.dragStart = Vektor(x = it.x, y = it.y) }
        this.DOWN_IMG.self.setOnMouseReleased { this.self_onMouseReleased(me = it) }
        this.DOWN_IMG.self.setOnMouseDragged { this.self_onMouseDragg(me = it) }

        this.naslednjiDelB.setOnAction {
            this.indexDelOdseka++
            this.init_top_img()
        }
        this.prejsnjiDelB.setOnAction {
            this.indexDelOdseka--
            this.init_top_img()
        }
        this.naslednjaNalogaB.setOnAction {
            this.indexOdseka++
            this.init_odsek()
        }
        this.resetirajNalogoB.setOnAction {
            val delNaloge = this.getDelNaloge()
            delNaloge.anotacije = mutableListOf(delNaloge.anotacije.first())
            this.init_down_img()
            this.init_top_img()
        }
        this.prejsnjaNalogaB.setOnAction {
            this.indexOdseka--
            this.init_odsek()
        }
    }

    fun init(stran: Stran) {
        this.indexOdseka = 0
        this.stran = stran
        this.odseki = this.razrezi_stran.zdaj(stran = stran)
        this.init_odsek()
    }

    private fun init_down_img() {
        this.DOWN_IMG.init(this.getOdsek().slika.img, sirokaSlika = true)
    }

    private fun init_top_img() {
        val imgs = this.getDelNaloge().anotacije.map {
            this.getOdsek().slika.img.getSubimage(it.x.toInt(), it.y.toInt(), it.width.toInt(), it.height.toInt())
        }.toMutableList()

        var img = imgs.removeAt(0)
        imgs.forEach { img = img.dodajSpodaj(it) }

        this.TOP_IMG.init(img, sirokaSlika = true)
        this.redraw_down_img()
    }

    private fun init_odsek() {
        this.indexDelOdseka = 0
        this.naloga = this.anotiraj_omego_nalogo.zdaj(odsek = this.getOdsek())
        this.init_down_img()
        this.init_top_img()
    }

    private fun redraw_down_img() {
        this.DOWN_IMG.pobrisiOzadje()
        this.getDelNaloge().anotacije.forEach { this.DOWN_IMG.narisi_rectangle(ano = it, color = Color.RED) }
    }

    private fun self_onMouseReleased(me: MouseEvent) {
        val anotacija = this.DOWN_IMG.anotacijaKvadrata(r = this.dragRectangle, text = "", tip = Anotacija.Tip.NALOGA)
        naloga.deli[this.indexDelOdseka].anotacije.add(anotacija)

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

    private fun getOdsek(): Odsek {
        //Popravi indeks ce je izven meje
        if (this.indexOdseka >= this.odseki.size) {
            this.indexOdseka = this.odseki.size - 1
        } else if (this.indexOdseka < 0)
            this.indexOdseka = 0

        //Dobi odsek
        return this.odseki[this.indexOdseka]
    }

    private fun getDelNaloge(): DelNaloge {
        //Popravi indeks ce je izven meje
        if (this.indexDelOdseka >= naloga.deli.size) {
            this.indexDelOdseka = naloga.deli.size - 1
        } else if (this.indexDelOdseka < 0)
            this.indexDelOdseka = 0

        //Dobi odsek
        return naloga.deli[this.indexDelOdseka]
    }

}
