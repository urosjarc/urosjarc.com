package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.app.widgets.BarveAnotacij
import gui.domain.Odsek
import gui.domain.Stran
import gui.domain.Vektor
import gui.extend.vektor
import gui.use_cases.Razrezi_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage


abstract class Rezanje_slike_Ui : KoinComponent {
    @FXML
    lateinit var self: BorderPane

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var debugirajB: Button

    @FXML
    lateinit var debugingS: Slider

    @FXML
    lateinit var potrdiB: Button

    @FXML
    lateinit var odsekL: Label

    val IMG: ImageView_BufferedImage get() = this.imageView_bufferedImage_Controller
}

open class Rezanje_slike : Rezanje_slike_Ui() {
    val razrezi_stran by this.inject<Razrezi_stran>()

    lateinit var zadnjiMouseEvent: MouseEvent

    lateinit var slika: BufferedImage
    var stran = Stran.PRAZNA
    var odseki = mutableListOf<Odsek>()
    var mouseOdseki = setOf<Odsek>()
    var izbranOdsek: Odsek? = null

    @FXML
    fun initialize() {
        this.IMG.zoom.opazuj { this.na_novo_narisi_odseke_v_ozadju() }
        this.IMG.self.setOnMousePressed { this.onMousePressed(me = it) }
        this.IMG.self.setOnMouseDragged { this.onMouseDragg(me = it) }
        this.IMG.self.setOnMouseMoved { this.onMouseMove(me = it) }
        this.resetirajB.setOnAction { this.razrezi_stran_in_na_novo_narisi_odseke() }
        this.debugirajB.setOnAction { this.razrezi_stran_in_na_novo_narisi_odseke(debug = true) }
    }

    fun init(slika: BufferedImage, stran: Stran, odseki: MutableList<Odsek>?) {
        this.slika = slika
        this.IMG.init(slika = slika)
        this.stran = stran
        this.odseki = odseki ?: this.razrezi_stran.zdaj(slika = slika, stran = stran)
        this.na_novo_narisi_odseke_v_ozadju()
    }

    @OptIn(DelicateCoroutinesApi::class)
    open fun razrezi_stran_in_na_novo_narisi_odseke(debug: Boolean = false) {
        val self = this
        GlobalScope.launch(Dispatchers.Main) {
            self.odseki = self.razrezi_stran.zdaj(stran = self.stran, slika = self.slika)

            if (debug) {
                self.razrezi_stran.debug.forEach {
                    self.IMG.pobrisi_ozadje()
                    self.IMG.narisi_okvir(okvir = it, color = Color.RED, round = 0)
                    delay(self.debugingS.value.toLong())
                }
            }

            self.na_novo_narisi_odseke_v_ozadju()
        }
    }


    fun na_novo_narisi_odseke_v_ozadju(narisiNaloge: Boolean = true) {
        this.IMG.pobrisi_ozadje()
        this.odseki.forEach { odsek ->
            when (odsek.tip) {
                Odsek.Tip.NALOGA -> {
                    if (narisiNaloge) this.IMG.narisi_okvir(odsek.okvir, BarveAnotacij.NALOGA.value, round = 15)
                    odsek.pododseki.forEach {
                        this.IMG.narisi_okvir(it.okvir, BarveAnotacij.PODNALOGA.value, round = 15)
                    }
                }

                Odsek.Tip.TEORIJA -> this.IMG.narisi_okvir(odsek.okvir, BarveAnotacij.TEORIJA.value, round = 15)
                Odsek.Tip.NASLOV -> this.IMG.narisi_okvir(odsek.okvir, BarveAnotacij.NASLOV.value, round = 15)
                Odsek.Tip.GLAVA -> {
                    this.IMG.narisi_okvir(odsek.okvir, BarveAnotacij.NOGA.value, round = 15)
                    odsek.pododseki.forEachIndexed { i, podsek -> this.IMG.narisi_okvir(podsek.okvir, BarveAnotacij.PODNALOGA.value, round = 15) }
                }

                Odsek.Tip.NEZNANO -> TODO()
                Odsek.Tip.PODNALOGA -> TODO()
            }
        }
        this.mouseOdseki.forEach { this.IMG.narisi_okvir(it.okvir, Color.BLACK, round = 0) }
    }

    private fun onMouseMove(me: MouseEvent) {

        this.odsekL.text = ""
        if (!me.isPrimaryButtonDown) {
            val vektor = this.IMG.mapiraj(v = me.vektor, noter = false)
            this.mouseOdseki = this.odsekiV(vektor = vektor)
            if (this.mouseOdseki.isNotEmpty()) {
                val text = this.odseki.filter { odsek -> odsek.okvir.vsebuje(vektor = me.vektor) }.firstOrNull()?.tekst ?: ""
                this.odsekL.text = if (text.isEmpty()) "" else "\"${text}\""
                this.na_novo_narisi_odseke_v_ozadju()
            }
        }

    }

    private fun onMousePressed(me: MouseEvent) {
        this.zadnjiMouseEvent = me
        if (me.isPrimaryButtonDown) this.IMG.scrollPane.isPannable = false
        val vektor = this.IMG.mapiraj(me.vektor, noter = false)
        this.izbranOdsek = this.odsekiV(vektor = vektor).firstOrNull()
    }

    private fun onMouseDragg(me: MouseEvent) {
        if (!me.isPrimaryButtonDown) return

        val vektor = this.IMG.mapiraj(me.vektor, noter = false)
        if(this.izbranOdsek != null) {
            this.izbranOdsek!!.okvir.end = vektor
            this.na_novo_narisi_odseke_v_ozadju()
        }
    }

    fun odsekiV(vektor: Vektor): Set<Odsek> {
        val pododseki = mutableSetOf<Odsek>()
        this.odseki.forEach {
            it.pododseki.forEach { po -> if (po.okvir.vsebuje(vektor = vektor)) pododseki.add(po) }
        }
        val ret = mutableSetOf<Odsek>()
        val najmanjsi = this.odseki.filter { it.okvir.vsebuje(vektor = vektor) }.toSet().union(pododseki).minByOrNull { it.okvir.povrsina }
        if (najmanjsi != null) ret.add(najmanjsi)
        return ret
    }
}
