package gui.app.parts

import gui.app.elements.ImageView_BufferedImage
import gui.domain.Anotacija
import gui.domain.Slika
import gui.domain.Stran
import gui.domain.Vektor
import gui.extend.vsebovani
import gui.services.LogService
import gui.services.OcrService
import gui.use_cases.Anotiraj_omego_stran
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class Anotiranje_slike_Ui : KoinComponent {
    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    val IMG: ImageView_BufferedImage get() = this.imageView_bufferedImage_Controller
}

class Anotiranje_slike : Anotiranje_slike_Ui() {
    val log by this.inject<LogService>()
    val procesirajOmegoSliko by this.inject<Anotiraj_omego_stran>()
    val ocrService by this.inject<OcrService>()
    val contextMenu = ContextMenu()

    lateinit var dragStart: Vektor
    var dragRectangle: Rectangle = Rectangle()

    private lateinit var slika: Slika
    lateinit var stran: Stran

    private var userAnotacije = mutableSetOf<Anotacija>()
    private var anotacije = listOf<Anotacija>()

    @FXML
    fun initialize() {
        println("init Anotiranje_zip_slike")

        // Popravi anotacije ce se slika zoomira
        this.IMG.zoom.opazuj { this.redraw_img() }
        this.IMG.self.setOnMousePressed { this.dragStart = Vektor(x = it.x, y = it.y) }
        this.IMG.self.setOnMouseReleased { this.self_onMouseReleased(me = it) }
        this.IMG.self.setOnMouseDragged { this.self_onMouseDragg(me = it) }
        this.resetirajB.setOnAction { this.init_img() }

        // Dodajanje tipov anotacij v context menu slike
        Anotacija.Tip.entries.forEach {
            val menuItem1 = MenuItem(it.name)
            menuItem1.userData = it
            this.contextMenu.items.add(menuItem1)
        }

        // Ko se izbere context menu shrani izbrane anotacije in jih prikazi na strani.
        this.contextMenu.setOnAction {

            val tip = (it.target as MenuItem).userData as Anotacija.Tip
            if (tip == Anotacija.Tip.NEZNANO) this.stran.odstrani(this.userAnotacije.toList())
            else this.stran.dodaj(ano = this.userAnotacije.toList(), tip = tip)

            this.userAnotacije.clear()
            this.redraw_img()
        }
    }

    fun init(zipSlika: Slika) {
        this.slika = zipSlika
        this.anotacije = this.ocrService.google(image = zipSlika.img)
        this.init_img()
    }

    private fun init_img() {
        this.log.info("init: ${this.slika}")
        this.stran = this.procesirajOmegoSliko.zdaj(slika = this.slika, anos = this.anotacije)
        this.IMG.init(img = this.slika.img)
        this.redraw_img()
    }

    private fun redraw_img() {
        this.IMG.pobrisiOzadje()
        this.stran.let { stran ->
            stran.noga.forEach { this.IMG.narisi_rectangle(it, Color.BLACK) }
            stran.naloge.forEach { it -> it.forEach { this.IMG.narisi_rectangle(it, Color.GREEN) } }
            stran.naslov.forEach { this.IMG.narisi_rectangle(it, Color.BLUE) }
            stran.glava.forEach { this.IMG.narisi_rectangle(it, Color.BLACK) }
            stran.teorija.forEach { this.IMG.narisi_rectangle(it, Color.RED) }
        }
        this.userAnotacije.forEach { this.IMG.narisi_rectangle(it, Color.MAGENTA) }
    }

    private fun self_onMouseReleased(me: MouseEvent) {
        contextMenu.show(this.IMG.self, me.screenX, me.screenY)

        val dragAnotacija = this.IMG.anotacijaKvadrata(r=this.dragRectangle, text="", tip=Anotacija.Tip.NEZNANO)
        this.userAnotacije = this.anotacije.vsebovani(dragAnotacija).toMutableSet()

        //Vse skupaj se enkrat narisi
        this.redraw_img()
    }

    private fun self_onMouseDragg(me: MouseEvent) {
        //Odstrani drag rectangle
        this.IMG.backgroundP.children.remove(this.dragRectangle)

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
        this.IMG.backgroundP.children.add(this.dragRectangle)
    }
}
