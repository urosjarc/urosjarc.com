package gui.app.parts

import gui.domain.Stran
import gui.app.elements.ImageView_BufferedImage
import gui.domain.Anotacija
import gui.domain.Naloga
import gui.domain.Odsek
import gui.use_cases.Anotiraj_omego_nalogo
import gui.use_cases.Razrezi_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Rezanje_slike : KoinComponent {

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    val CTRL get() = this.imageView_bufferedImage_Controller

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    val anotiraj_omego_nalogo by this.inject<Anotiraj_omego_nalogo>()
    val razrezi_stran by this.inject<Razrezi_stran>()

    var stran: Stran? = null
    var naloga: Naloga? = null
    var odseki = listOf<Odsek>()
    var naloge = mutableListOf<Naloga>()
    val odsek: Odsek
        get() {
            //Popravi indeks ce je izven meje
            if (this.index >= this.odseki.size)
                this.index = this.odseki.size - 1
            else if (this.index < 0)
                this.index = 0

            //Dobi odsek
            return this.odseki[this.index]
        }

    var index = 0

    fun init(stran: Stran) {
        this.stran = stran
        this.odseki = this.razrezi_stran.zdaj(stran = stran)

        this.potrdiB.setOnAction {
            this.index++
            this.init_imageView()
        }
        this.resetirajB.setOnAction {
            this.index--
            this.init_imageView()
        }

        this.init_imageView()
    }

    fun init_imageView() {
        //Ce je naloga za ta odsek ze bila ustvarjena potem uzemi njo!
        val obstojeceNaloge = this.naloge.filter { it.odsek == this.odsek }
        if (obstojeceNaloge.isEmpty()) {
            val naloga = this.anotiraj_omego_nalogo.zdaj(this.odsek)
            this.naloge.add(naloga)
            this.naloga = naloga
        } else {
            this.naloga = obstojeceNaloge.first()
        }

        //Narisi sliko
        this.CTRL.init(this.odsek.img, sirokaSlika = true)
    }

    @FXML
    fun initialize() {
        println("init Rezanje_zip_slike")
        ImageView_BufferedImage.zoom.opazuj { this.redraw_imageView() }
    }

    private fun redraw_imageView() {
        this.CTRL.pobrisiOzadje()
        if(this.odseki.isEmpty()) return

        val ano = Anotacija(0.0, 0.0, this.odsek.img.height.toDouble(), this.odsek.img.width.toDouble(), text = "", tip = Anotacija.Tip.NALOGA)
        val rec = this.CTRL.kvadratAnotacije(ano, Color.RED)
        this.CTRL.backgroundP.children.add(rec)
        this.naloga!!.podnaloge.forEach {
            val rec = this.CTRL.kvadratAnotacije(it, Color.BLUE)
            this.CTRL.backgroundP.children.add(rec)
        }
    }
}
