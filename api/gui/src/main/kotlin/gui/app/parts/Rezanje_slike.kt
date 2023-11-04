package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.app.widgets.BarveAnotacij
import gui.domain.Odsek
import gui.domain.Okvir
import gui.domain.Stran
import gui.domain.Vektor
import gui.extend.vektor
import gui.use_cases.Razrezi_stran
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
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
    lateinit var potrdiB: Button

    @FXML
    lateinit var odsekL: Label

    enum class Akcija { ODSTRANI, USTVARI }

    val IMG: ImageView_BufferedImage get() = this.imageView_bufferedImage_Controller
}

open class Rezanje_slike : Rezanje_slike_Ui() {
    val razrezi_stran by this.inject<Razrezi_stran>()

    var dragOkvir = Okvir.PRAZEN
    var dragRectangle: Rectangle = Rectangle()
    lateinit var zadnjiMouseEvent: MouseEvent

    lateinit var slika: BufferedImage
    var stran = Stran.PRAZNA
    var odseki = mutableListOf<Odsek>()
    var userOdseki = setOf<Odsek>()
    var mouseOdseki = setOf<Odsek>()

    @FXML
    fun initialize() {
        this.IMG.zoom.opazuj { this.na_novo_narisi_odseke_v_ozadju() }
        this.IMG.self.setOnMousePressed { this.onMousePressed(me = it) }
        this.IMG.self.setOnMouseReleased { this.onMouseReleased(me = it) }
        this.IMG.self.setOnMouseDragged { this.onMouseDragg(me = it) }
        this.IMG.self.setOnMouseMoved { this.onMouseMove(me = it) }
        this.resetirajB.setOnAction { this.razrezi_stran_in_na_novo_narisi_odseke() }
    }

    fun init(slika: BufferedImage, stran: Stran, odseki: MutableList<Odsek>?) {
        this.slika = slika
        this.IMG.init(slika = slika)
        this.stran = stran
        this.odseki = odseki ?: this.razrezi_stran.zdaj(slika = slika, stran = stran)
        this.na_novo_narisi_odseke_v_ozadju()
    }

    open fun razrezi_stran_in_na_novo_narisi_odseke() {
        this.odseki = this.razrezi_stran.zdaj(stran = this.stran, slika = this.slika)
        this.na_novo_narisi_odseke_v_ozadju()
    }

    fun na_novo_narisi_odseke_v_ozadju(narisiDragRec: Boolean = false, narisiNaloge: Boolean = false) {
        this.IMG.pobrisi_ozadje()
        this.odseki.forEach { odsek ->
            when (odsek.tip) {
                Odsek.Tip.NALOGA -> {
                    if (narisiNaloge || odsek.pododseki.isEmpty()) this.IMG.narisi_okvir(odsek.okvir, BarveAnotacij.NALOGA.value, round = 15)
                    odsek.pododseki.forEachIndexed { i, pododsek ->
                        val barva = if (i == 0) BarveAnotacij.NALOGA else BarveAnotacij.PODNALOGA
                        this.IMG.narisi_okvir(pododsek.okvir, barva.value, round = 15)
                    }
                }

                Odsek.Tip.TEORIJA -> this.IMG.narisi_okvir(odsek.okvir, BarveAnotacij.TEORIJA.value, round = 15)
                Odsek.Tip.NASLOV -> this.IMG.narisi_okvir(odsek.okvir, BarveAnotacij.NASLOV.value, round = 15)
                Odsek.Tip.GLAVA -> {
                    odsek.pododseki.forEachIndexed { i, pododsek -> this.IMG.narisi_okvir(pododsek.okvir, BarveAnotacij.PODNALOGA.value, round = 15) }
                }

                Odsek.Tip.NEZNANO -> TODO()
                Odsek.Tip.PODNALOGA -> TODO()
            }
        }
        this.userOdseki.forEach { this.IMG.narisi_okvir(it.okvir, Color.BLACK, round = 0) }
        this.mouseOdseki.forEach { this.IMG.narisi_okvir(it.okvir, Color.BLACK, round = 0) }
        if (narisiDragRec) this.IMG.backgroundP.children.add(this.dragRectangle)
    }

    private fun onMouseMove(me: MouseEvent) {
        this.userOdseki = setOf()
        this.IMG.pobrisi_ozadje()

        this.odsekL.text = ""
        if (!me.isSecondaryButtonDown && !me.isMiddleButtonDown) {
            val vektor = this.IMG.mapiraj(v = me.vektor, noter = false)
            this.mouseOdseki = this.odsekiV(vektor = vektor)
            if (this.mouseOdseki.isNotEmpty()) {
                val text = this.odseki.filter { odsek -> odsek.okvir.vsebuje(vektor = me.vektor) }.firstOrNull()?.tekst ?: ""
                this.odsekL.text = if (text.isEmpty()) "" else "\"${text}\""
            }
        }

        this.na_novo_narisi_odseke_v_ozadju()
    }

    private fun onMousePressed(me: MouseEvent) {
        this.zadnjiMouseEvent = me
        if (me.isSecondaryButtonDown || me.isMiddleButtonDown) this.IMG.scrollPane.isPannable = false
        this.dragOkvir.start = me.vektor
        this.mouseOdseki = setOf()
        this.na_novo_narisi_odseke_v_ozadju()
    }

    private fun onMouseDragg(me: MouseEvent) {
        if (!me.isSecondaryButtonDown && !me.isMiddleButtonDown) return
        this.IMG.backgroundP.children.remove(this.dragRectangle)

        this.dragOkvir.end = me.vektor
        this.dragRectangle = this.dragOkvir.vRectangle(width = 1.0, round = 0)
        this.dragRectangle.strokeWidth = 1.0
        this.dragRectangle.strokeDashArray.addAll(5.0)

        this.IMG.backgroundP.children.add(this.dragRectangle)
    }

    private fun onMouseReleased(me: MouseEvent) {
        this.IMG.scrollPane.isPannable = true
        if (!this.zadnjiMouseEvent.isSecondaryButtonDown && !this.zadnjiMouseEvent.isMiddleButtonDown) return

        this.dragOkvir.end = me.vektor
        this.dragRectangle = this.dragOkvir.vRectangle(round = 0)

        val okvir = this.IMG.vOkvir(r = this.dragRectangle)
        val izbraniOkvirji = this.odsekiV(vektor = okvir.end)

        this.userOdseki = this.odsekiV(okvir = okvir).union(izbraniOkvirji)
        this.mouseOdseki = setOf()

        this.na_novo_narisi_odseke_v_ozadju(narisiDragRec = this.userOdseki.isEmpty())

        if (this.zadnjiMouseEvent.isSecondaryButtonDown) this.onAction(am = null, akcija = Akcija.ODSTRANI)
        if (this.zadnjiMouseEvent.isMiddleButtonDown) this.onAction(am = null, akcija = Akcija.ODSTRANI)
    }

    private fun onAction(am: ActionEvent?, akcija: Akcija) {
        val okvir = this.IMG.vOkvir(r = this.dragRectangle)

        when (akcija) {
            Akcija.ODSTRANI -> this.odseki.removeAll(this.userOdseki)
            Akcija.USTVARI -> {
                println("Create logic for creating odseke!")
            }
        }

        this.userOdseki = setOf()
        this.na_novo_narisi_odseke_v_ozadju()
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

    fun odsekiV(okvir: Okvir): Set<Odsek> {
        val pododseki = mutableSetOf<Odsek>()
        this.odseki.forEach {
            it.pododseki.forEach { po -> if (po.okvir.vsebuje(okvir = okvir)) pododseki.add(po) }
        }
        val ret = mutableSetOf<Odsek>()
        val najmanjsi = this.odseki.filter { it.okvir.vsebuje(okvir = okvir) }.toSet().union(pododseki).minByOrNull { it.okvir.povrsina }
        if (najmanjsi != null) ret.add(najmanjsi)
        return ret
    }
}
