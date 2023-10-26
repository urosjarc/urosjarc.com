package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.domain.Anotacija
import gui.domain.Okvir
import gui.domain.Stran
import gui.extend.vOkvirju
import gui.extend.vektor
import gui.services.OcrService
import gui.use_cases.Anotiraj_omego_stran
import javafx.collections.FXCollections
import javafx.collections.ObservableArrayBase
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.image.BufferedImage

abstract class Anotiranje_slike_Ui : KoinComponent {
    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    val IMG: ImageView_BufferedImage get() = this.imageView_bufferedImage_Controller

    enum class Akcija { ODSTRANI, USTVARI, IZBERI }

    val dodajM = Menu("Dodaj...")
    val ustvariM = Menu("Ustvari...")
    val odstraniMI = MenuItem("Odstrani...")
    val contextMenu = ContextMenu()
}

open class Anotiranje_slike : Anotiranje_slike_Ui() {
    val anotiraj_omego_stran by this.inject<Anotiraj_omego_stran>()
    val ocrService by this.inject<OcrService>()

    var dragOkvir = Okvir.PRAZEN
    var dragRectangle: Rectangle = Rectangle()

    lateinit var slika: BufferedImage
    var anotacije = listOf<Anotacija>()
    var stran = Stran.PRAZNA
    var userOkvirji = setOf<Okvir>()
    var mouseOkvirji = setOf<Okvir>()

    @FXML
    fun initialize() {
        println("init Anotiranje_zip_slike")

        // Dodajanje tipov anotacij v context menu slike
        Anotacija.Tip.entries.forEach { tip ->
            when (tip) {
                Anotacija.Tip.DODATNO -> {}
                else -> {
                    val menuItem = MenuItem(tip.name).also { it.userData = tip }
                    val menuItem2 = MenuItem(tip.name).also { it.userData = tip }
                    this.dodajM.items.add(menuItem)
                    this.ustvariM.items.add(menuItem2)
                }
            }
        }
        this.contextMenu.items.addAll(this.dodajM, this.odstraniMI, this.ustvariM)

        // Popravi anotacije ce se slika zoomira
        this.IMG.zoom.opazuj { this.na_novo_narisi_anotacije_v_ozadju() }
        this.IMG.self.setOnMousePressed { this.onMousePressed(me = it) }
        this.IMG.self.setOnMouseReleased { this.onMouseReleased(me = it) }
        this.IMG.self.setOnMouseDragged { this.onMouseDragg(me = it) }
        this.IMG.self.setOnMouseMoved { this.onMouseMove(me = it) }
        this.resetirajB.setOnAction { this.anotiraj_stran_in_na_novo_narisi_anotacije() }
        this.dodajM.setOnAction { this.onContextAction(am = it, akcija = Akcija.IZBERI) }
        this.ustvariM.setOnAction { this.onContextAction(am = it, akcija = Akcija.USTVARI) }
        this.odstraniMI.setOnAction { this.onContextAction(am = it, akcija = Akcija.ODSTRANI) }
    }

    fun init(slika: BufferedImage) {
        this.slika = slika
        this.anotacije = this.ocrService.google(image = slika)
        this.IMG.init(slika = slika)
        this.anotiraj_stran_in_na_novo_narisi_anotacije()
    }

    open fun anotiraj_stran_in_na_novo_narisi_anotacije() {
        this.stran = this.anotiraj_omego_stran.zdaj(img = this.slika, anotacije = this.anotacije)
        this.na_novo_narisi_anotacije_v_ozadju()
    }

    fun na_novo_narisi_anotacije_v_ozadju(narisiDragRec: Boolean = false) {
        this.IMG.pobrisiOzadje()
        this.stran.let { stran ->
            stran.noga.forEach { this.IMG.narisi_okvir(it, Color.BLACK) }
            stran.naloge.forEach { this.IMG.narisi_okvir(it, Color.GREEN) }
            stran.podnaloge.forEach { this.IMG.narisi_okvir(it, Color.MAGENTA) }
            stran.naslov.forEach { this.IMG.narisi_okvir(it, Color.BLUE) }
            stran.teorija.forEach { this.IMG.narisi_okvir(it, Color.RED) }
            stran.dodatno.forEach { this.IMG.narisi_okvir(it, Color.GREEN) }
        }
        this.userOkvirji.forEach { this.IMG.narisi_okvir(it, Color.MAGENTA) }
        this.mouseOkvirji.forEach { this.IMG.narisi_okvir(it, Color.BLUE) }
        if (narisiDragRec) this.IMG.backgroundP.children.add(this.dragRectangle)
    }

    private fun onMouseMove(me: MouseEvent) {
        this.contextMenu.hide()
        this.userOkvirji = setOf()
        this.IMG.pobrisiOzadje()
        val vektor = this.IMG.mapiraj(v = me.vektor, noter = false)
        this.mouseOkvirji = this.stran.okvirjiV(vektor = vektor)
        this.na_novo_narisi_anotacije_v_ozadju()
    }

    private fun onMousePressed(me: MouseEvent) {
        this.dragOkvir.start = me.vektor
        this.mouseOkvirji = setOf()
        this.na_novo_narisi_anotacije_v_ozadju()
    }

    private fun onMouseDragg(me: MouseEvent) {
        this.IMG.backgroundP.children.remove(this.dragRectangle)

        this.dragOkvir.end = me.vektor
        this.dragRectangle = this.dragOkvir.vRectangle(color = Color.RED)
        this.dragRectangle.strokeWidth = 1.0
        this.dragRectangle.strokeDashArray.addAll(5.0)

        this.IMG.backgroundP.children.add(this.dragRectangle)
    }

    private fun onMouseReleased(me: MouseEvent) {
        this.dragOkvir.end = me.vektor
        this.dragRectangle = this.dragOkvir.vRectangle(color = Color.RED)
        val okvir = this.IMG.vOkvir(r = this.dragRectangle)

        val izbraniOkvirji = this.stran.okvirjiV(vektor = okvir.end)
        this.userOkvirji = this.stran.okvirji.vOkvirju(okvir = okvir).union(izbraniOkvirji)

        this.mouseOkvirji = setOf()
        this.na_novo_narisi_anotacije_v_ozadju(narisiDragRec = this.userOkvirji.isEmpty())
        this.contextMenu.show(this.IMG.self, me.screenX, me.screenY)
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
