package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.domain.*
import gui.use_cases.Anotiraj_omego_nalogo
import gui.use_cases.Razrezi_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Rezanje_slike : KoinComponent {

    val razrezi_stran by this.inject<Razrezi_stran>()

    val anotiraj_omego_nalogo by this.inject<Anotiraj_omego_nalogo>()

    @FXML
    lateinit var top_imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var down_imageView_bufferedImage_Controller: ImageView_BufferedImage
    val TOP_CTRL get() = this.top_imageView_bufferedImage_Controller
    val DOWN_CTRL get() = this.down_imageView_bufferedImage_Controller

    @FXML
    lateinit var resetirajNalogoB: Button

    @FXML
    lateinit var potrdiNalogoB: Button

    @FXML
    lateinit var resetirajDelNalogeB: Button

    @FXML
    lateinit var potrdiDelNalogeB: Button

    var stran: Stran? = null
    var odseki = listOf<Odsek>()
    var indexOdseka = 0
    var indexAno = 0
    var naloga: Naloga? = null

    val odsek
        get(): Odsek {
            //Popravi indeks ce je izven meje
            if (this.indexOdseka >= this.odseki.size) {
                this.indexOdseka = this.odseki.size - 1
            } else if (this.indexOdseka < 0)
                this.indexOdseka = 0

            //Dobi odsek
            return this.odseki[this.indexOdseka]
        }

    val anotacija
        get(): Anotacija? {
            if (naloga == null) return null
            val anos = mutableListOf<Anotacija>()
            if (naloga!!.glava.isNotEmpty()) anos.add(naloga!!.glava.first())
            anos.addAll(naloga!!.podnaloge)

            //Popravi indeks ce je izven meje
            if (this.indexAno >= anos.size) {
                this.indexAno = anos.size - 1
            } else if (this.indexAno < 0)
                this.indexAno = 0

            //Dobi odsek
            return anos[this.indexAno]
        }

    fun init(stran: Stran) {
        this.indexOdseka = 0
        this.stran = stran
        this.odseki = this.razrezi_stran.zdaj(stran = stran)
        this.init_odsek()
    }

    fun init_odsek() {
        this.indexAno = 0
        this.naloga = this.anotiraj_omego_nalogo.zdaj(odsek = this.odsek)
        this.init_down_imageView()
        this.init_top_imageView()
    }

    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
        this.DOWN_CTRL.zoom.opazuj { this.redraw_down_imageView() }
        this.potrdiDelNalogeB.setOnAction {
            this.indexAno++
            this.init_top_imageView()
        }
        this.resetirajDelNalogeB.setOnAction {
            this.indexAno--
            this.init_top_imageView()
        }
        this.potrdiNalogoB.setOnAction {
            this.indexOdseka++
            this.init_odsek()
        }
        this.resetirajNalogoB.setOnAction {
            this.indexOdseka--
            this.init_odsek()
        }
    }

    fun init_down_imageView() {
        this.DOWN_CTRL.init(this.odsek!!.img, sirokaSlika = true)
    }

    fun init_top_imageView() {
        val a = this.anotacija ?: return
        val img = this.odsek!!.img.getSubimage(a.x.toInt(), a.y.toInt(), a.width.toInt(), a.height.toInt())
        this.TOP_CTRL.init(img, sirokaSlika = true)
        this.redraw_down_imageView()
    }

    private fun redraw_down_imageView() {
        val a = this.anotacija ?: return
        this.DOWN_CTRL.pobrisiOzadje()
        val rec = this.DOWN_CTRL.kvadratAnotacije(ano = a, color = Color.RED)
        this.DOWN_CTRL.backgroundP.children.add(rec)
    }
}
