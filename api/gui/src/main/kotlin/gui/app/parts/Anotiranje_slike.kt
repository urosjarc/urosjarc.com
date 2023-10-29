package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.app.widgets.BarveAnotacij
import gui.domain.Anotacija
import gui.domain.Okvir
import gui.domain.Stran
import gui.extend.vOkvirju
import gui.extend.vektor
import gui.services.OcrService
import gui.use_cases.Anotiraj_omego_stran
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage


abstract class Anotiranje_slike_Ui : KoinComponent {
    @FXML
    lateinit var self: BorderPane

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    @FXML
    lateinit var anotacijaL: Label

    val IMG: ImageView_BufferedImage get() = this.imageView_bufferedImage_Controller

    enum class Akcija { ODSTRANI, USTVARI, IZBERI }

    val ustvariM = Menu("Ustvari...")
    val odstraniMI = MenuItem("Odstrani...")
    val dodajCM = ContextMenu()
    val ostaloCM = ContextMenu()
}

open class Anotiranje_slike : Anotiranje_slike_Ui() {
    val anotiraj_omego_stran by this.inject<Anotiraj_omego_stran>()
    val ocrService by this.inject<OcrService>()

    var dragOkvir = Okvir.PRAZEN
    var dragRectangle: Rectangle = Rectangle()
    lateinit var zadnjiMouseEvent: MouseEvent

    lateinit var slika: BufferedImage
    var anotacije = listOf<Anotacija>()
    var stran = Stran.PRAZNA
    var userOkvirji = setOf<Okvir>()
    var mouseOkvirji = setOf<Okvir>()

    @FXML
    fun initialize() {

        // Dodajanje tipov anotacij v context menu slike
        Anotacija.Tip.entries.forEach { tip ->
            when (tip) {
                Anotacija.Tip.DODATNO -> {
                    val menuItem = MenuItem(tip.name).also { it.userData = tip }
                    this.ustvariM.items.add(menuItem)
                }

                else -> {
                    val menuItem = MenuItem(tip.name).also { it.userData = tip }
                    val menuItem2 = MenuItem(tip.name).also { it.userData = tip }
                    this.dodajCM.items.add(menuItem)
                    this.ustvariM.items.add(menuItem2)
                }
            }
        }
        this.ostaloCM.items.addAll(this.odstraniMI, this.ustvariM)

        // Popravi anotacije ce se slika zoomira
        this.IMG.zoom.opazuj { this.na_novo_narisi_anotacije_v_ozadju() }
        this.IMG.self.setOnMousePressed { this.onMousePressed(me = it) }
        this.IMG.self.setOnMouseReleased { this.onMouseReleased(me = it) }
        this.IMG.self.setOnMouseDragged { this.onMouseDragg(me = it) }
        this.IMG.self.setOnMouseMoved { this.onMouseMove(me = it) }
        this.resetirajB.setOnAction { this.anotiraj_stran_in_na_novo_narisi_anotacije() }
        this.dodajCM.setOnAction { this.onContextAction(am = it, akcija = Akcija.IZBERI) }
        this.ustvariM.setOnAction { this.onContextAction(am = it, akcija = Akcija.USTVARI) }
        this.odstraniMI.setOnAction { this.onContextAction(am = it, akcija = Akcija.ODSTRANI) }
        this.self.setOnKeyPressed { println("key"); this.IMG.pobrisi_ozadje() }
        this.self.setOnKeyReleased { println("key release"); this.na_novo_narisi_anotacije_v_ozadju() }
    }

    fun init(slika: BufferedImage, stran: Stran?) {
        this.slika = slika
        this.IMG.init(slika = slika)

        if (stran != null) {
            this.stran = stran
            this.anotacije = stran.anotacije.toList()
            this.na_novo_narisi_anotacije_v_ozadju()
        } else {
            this.anotacije = this.ocrService.google(image = slika)
            this.anotiraj_stran_in_na_novo_narisi_anotacije()
        }
    }

    open fun anotiraj_stran_in_na_novo_narisi_anotacije() {
        this.stran = this.anotiraj_omego_stran.zdaj(img = this.slika, anotacije = this.anotacije)
        this.na_novo_narisi_anotacije_v_ozadju()
    }

    fun na_novo_narisi_anotacije_v_ozadju(narisiDragRec: Boolean = false) {
        this.IMG.pobrisi_ozadje()
        this.stran.let { stran ->
            stran.noga.forEach { this.IMG.narisi_okvir(it, BarveAnotacij.NOGA.value, round = true) }
            stran.naloge.forEach { this.IMG.narisi_okvir(it, BarveAnotacij.NALOGE.value, round = true) }
            stran.podnaloge.forEach { this.IMG.narisi_okvir(it, BarveAnotacij.PODNALOGE.value, round = true) }
            stran.naslov.forEach { this.IMG.narisi_okvir(it, BarveAnotacij.NASLOV.value, round = true) }
            stran.teorija.forEach { this.IMG.narisi_okvir(it, BarveAnotacij.TEORIJA.value, round = true) }
            stran.dodatno.forEach { this.IMG.narisi_okvir(it, BarveAnotacij.DODATNO.value, round = false) }
        }
        this.userOkvirji.forEach { this.IMG.narisi_okvir(it, Color.BLACK, round = false) }
        this.mouseOkvirji.forEach { this.IMG.narisi_okvir(it, Color.BLACK, round = false) }
        if (narisiDragRec) this.IMG.backgroundP.children.add(this.dragRectangle)
    }

    private fun onMouseMove(me: MouseEvent) {
        this.dodajCM.hide()
        this.ostaloCM.hide()
        this.userOkvirji = setOf()
        this.IMG.pobrisi_ozadje()

        this.anotacijaL.text = ""
        if (!me.isPrimaryButtonDown && !me.isSecondaryButtonDown && !me.isMiddleButtonDown) {
            val vektor = this.IMG.mapiraj(v = me.vektor, noter = false)
            this.mouseOkvirji = this.stran.okvirjiV(vektor = vektor)
            if (this.mouseOkvirji.isNotEmpty()) {
                val text = this.stran.anotacije.vOkvirju(okvir = this.mouseOkvirji.first()).firstOrNull()?.text ?: ""
                this.anotacijaL.text = if (text.isEmpty()) "" else "\"${text}\""
            }
        }

        this.na_novo_narisi_anotacije_v_ozadju()
    }

    private fun onMousePressed(me: MouseEvent) {
        this.zadnjiMouseEvent = me
        if (me.isPrimaryButtonDown || me.isSecondaryButtonDown) this.IMG.scrollPane.isPannable = false
        this.dragOkvir.start = me.vektor
        this.mouseOkvirji = setOf()
        this.na_novo_narisi_anotacije_v_ozadju()
    }

    private fun onMouseDragg(me: MouseEvent) {
        if (!me.isPrimaryButtonDown && !me.isSecondaryButtonDown) return
        this.IMG.backgroundP.children.remove(this.dragRectangle)

        this.dragOkvir.end = me.vektor
        this.dragRectangle = this.dragOkvir.vRectangle(width = 1.0, round = false)
        this.dragRectangle.strokeWidth = 1.0
        this.dragRectangle.strokeDashArray.addAll(5.0)

        this.IMG.backgroundP.children.add(this.dragRectangle)
    }

    private fun onMouseReleased(me: MouseEvent) {
        this.IMG.scrollPane.isPannable = true
        if (!this.zadnjiMouseEvent.isPrimaryButtonDown && !this.zadnjiMouseEvent.isSecondaryButtonDown) return

        this.dragOkvir.end = me.vektor
        this.dragRectangle = this.dragOkvir.vRectangle(round = false)

        val okvir = this.IMG.vOkvir(r = this.dragRectangle)
        val izbraniOkvirji = this.stran.okvirjiV(vektor = okvir.end)

        this.userOkvirji = this.stran.okvirji.vOkvirju(okvir = okvir).union(izbraniOkvirji)
        this.mouseOkvirji = setOf()

        this.na_novo_narisi_anotacije_v_ozadju(narisiDragRec = this.userOkvirji.isEmpty())

        if (this.zadnjiMouseEvent.isPrimaryButtonDown) this.dodajCM.show(this.IMG.self, me.screenX, me.screenY)
        if (this.zadnjiMouseEvent.isSecondaryButtonDown) this.ostaloCM.show(this.IMG.self, me.screenX, me.screenY)
    }

    private fun onContextAction(am: ActionEvent, akcija: Akcija) {
        val okvir = this.IMG.vOkvir(r = this.dragRectangle)

        val tip = (am.target as MenuItem).userData as Anotacija.Tip? ?: Anotacija.Tip.NEZNANO
        when (akcija) {
            Akcija.ODSTRANI -> this.stran.odstrani(okvirji = this.userOkvirji)
            Akcija.USTVARI -> this.stran.dodaj(okvirji = setOf(okvir), tip = tip)
            Akcija.IZBERI -> this.stran.izberi(okvirji = this.userOkvirji, tip = tip)
        }

        this.userOkvirji = setOf()
        this.na_novo_narisi_anotacije_v_ozadju()
    }

}
