package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.domain.Anotacija
import gui.domain.Naloga
import gui.domain.Odsek
import gui.domain.Stran
import gui.use_cases.Anotiraj_omego_nalogo
import gui.use_cases.Razrezi_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class Rezanje_slike_Ui : KoinComponent {
    @FXML
    lateinit var top_imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var down_imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var resetirajNalogoB: Button

    @FXML
    lateinit var potrdiNalogoB: Button

    @FXML
    lateinit var resetirajDelNalogeB: Button

    @FXML
    lateinit var potrdiDelNalogeB: Button

    val TOP_IMG get() = this.top_imageView_bufferedImage_Controller
    val DOWN_IMG get() = this.down_imageView_bufferedImage_Controller
}

class Rezanje_slike : Rezanje_slike_Ui() {
    private val razrezi_stran by this.inject<Razrezi_stran>()
    private val anotiraj_omego_nalogo by this.inject<Anotiraj_omego_nalogo>()

    private lateinit var stran: Stran
    lateinit var naloga: Naloga

    private var odseki = listOf<Odsek>()
    private var indexOdseka = 0
    private var indexAno = 0

    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
        this.DOWN_IMG.zoom.opazuj { this.redraw_down_img() }
        this.potrdiDelNalogeB.setOnAction {
            this.indexAno++
            this.init_top_img()
        }
        this.resetirajDelNalogeB.setOnAction {
            this.indexAno--
            this.init_top_img()
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
        val a = this.getAnotacija()
        val img = this.getOdsek().slika.img.getSubimage(a.x.toInt(), a.y.toInt(), a.width.toInt(), a.height.toInt())
        this.TOP_IMG.init(img, sirokaSlika = true)
        this.redraw_down_img()
    }

    private fun redraw_down_img() {
        val a = this.getAnotacija()
        this.DOWN_IMG.pobrisiOzadje()
        val rec = this.DOWN_IMG.kvadratAnotacije(ano = a, color = Color.RED)
        this.DOWN_IMG.backgroundP.children.add(rec)
    }

    private fun init_odsek() {
        this.indexAno = 0
        this.naloga = this.anotiraj_omego_nalogo.zdaj(odsek = this.getOdsek())
        this.init_down_img()
        this.init_top_img()
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

    private fun getAnotacija(): Anotacija {
        val anos = mutableListOf<Anotacija>()
        if (naloga.glava.isNotEmpty()) anos.add(naloga.glava.first())
        anos.addAll(naloga.podnaloge)

        //Popravi indeks ce je izven meje
        if (this.indexAno >= anos.size) {
            this.indexAno = anos.size - 1
        } else if (this.indexAno < 0)
            this.indexAno = 0

        //Dobi odsek
        return anos[this.indexAno]
    }

}
