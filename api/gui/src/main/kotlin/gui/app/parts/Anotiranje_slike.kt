package gui.app.parts

import gui.domain.Anotacija
import gui.domain.Stran
import gui.domain.Vektor
import gui.domain.Slika
import gui.extend.*
import gui.app.elements.ImageView_BufferedImage
import gui.services.LogService
import gui.services.OcrService
import gui.use_cases.Anotiraj_omego_sliko
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Anotiranje_slike : KoinComponent {

    private val log by this.inject<LogService>()
    private val procesirajOmegoSliko by this.inject<Anotiraj_omego_sliko>()
    private val ocrService by this.inject<OcrService>()

    @FXML
    lateinit var resetirajB: Button

    @FXML
    lateinit var potrdiB: Button

    val contextMenu = ContextMenu()

    var data: Slika? = null

    /**
     * Anotacije
     */
    var userAnotacije = mutableSetOf<Anotacija>()
    var vseAnotacije = listOf<Anotacija>()
    var anotacijeStrani: Stran? = null

    /**
     * Drag
     */
    var dragStart: Vektor? = null
    var dragEnd: Vektor? = null
    var dragRectangle: Rectangle? = null

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage
    val CTRL: ImageView_BufferedImage get() = this.imageView_bufferedImage_Controller

    private fun eventPosition(mouseEvent: MouseEvent): Vektor = this.imagePosition(x = mouseEvent.x, y = mouseEvent.y)

    private fun imagePosition(x: Double, y: Double): Vektor {
        val img = this.data!!.img
        val widthR = img.width / this.CTRL.self.image.width
        val heightR = img.height / this.CTRL.self.image.height
        return Vektor(x = x * widthR, y = y * heightR)
    }

    private fun anotacijaPosition(ano: Anotacija): Rectangle {
        val v = this.imagePosition(x = ano.x, y = ano.y)
        val img = this.data!!.img
        val rx = img.width / this.CTRL.self.fitWidth
        val ry = img.height / this.CTRL.self.fitHeight
        return Rectangle(v.x / rx, v.y / ry, ano.width / rx, ano.height / ry)
    }

    @FXML
    fun initialize() {
        println("init Anotiranje_zip_slike")

        // Popravi anotacije ce se slika zoomira
        ImageView_BufferedImage.visina.opazuj { this.redraw_imageView() }
        this.CTRL.self.setOnMousePressed { this.dragStart = this.eventPosition(it) }
        this.CTRL.self.setOnMouseReleased { this.self_onMouseReleased(me = it) }
        this.CTRL.self.setOnMouseDragged { this.self_onMouseDragg(me = it) }
        this.resetirajB.setOnAction { this.init_imageView() }

        // Dodajanje tipov anotacij v context menu slike
        Anotacija.Tip.entries.forEach {
            val menuItem1 = MenuItem(it.name)
            menuItem1.userData = it
            this.contextMenu.items.add(menuItem1)
        }

        // Ko se izbere context menu shrani izbrane anotacije in jih prikazi na strani.
        this.contextMenu.setOnAction {

            val tip = (it.target as MenuItem).userData as Anotacija.Tip
            if (tip == Anotacija.Tip.NEZNANO) this.anotacijeStrani!!.odstrani(this.userAnotacije.toList())
            else this.anotacijeStrani!!.dodaj(ano = this.userAnotacije.toList(), tip = tip)

            this.userAnotacije.clear()
            this.redraw_imageView()
        }
    }

    fun self_onMouseReleased(me: MouseEvent) {
        this.dragEnd = this.eventPosition(me)
        contextMenu.show(this.CTRL.self, me.screenX, me.screenY)

        //Posodobi user anotacije
        this.userAnotacije.clear()
        this.vseAnotacije.forEach {
            val rec = this.anotacijaPosition(ano = it)
            val a = Vektor(x = rec.x + rec.width / 2.0, y = rec.y + rec.height / 2.0)
            if (a.x.vmes(this.dragStart!!.x, this.dragEnd!!.x) && a.y.vmes(this.dragStart!!.y, this.dragEnd!!.y)) {
                this.userAnotacije.add(it)
            }
        }

        //Vse skupaj se enkrat narisi
        this.redraw_imageView()
    }

    fun self_onMouseDragg(me: MouseEvent) {
        //Odstrani drag rectangle
        this.CTRL.backgroundP.children.remove(this.dragRectangle)

        //Ustvari drag rectangle
        val v = this.eventPosition(me)
        this.dragRectangle = Rectangle(
            this.dragStart!!.x,
            this.dragStart!!.y,
            v.x - this.dragStart!!.x,
            v.y - this.dragStart!!.y
        ).apply {
            this.fill = null
            this.stroke = Color.MAGENTA
            this.strokeWidth = 2.0
        }

        //Dodaj drag rectangle v background
        this.CTRL.backgroundP.children.add(this.dragRectangle)
    }

    private fun narisi_rectangle(ano: Anotacija, color: Color) {
        val rec = this.anotacijaPosition(ano)
        rec.fill = null
        rec.stroke = color
        rec.strokeWidth = 2.0
        this.CTRL.backgroundP.children.add(rec)
    }

    private fun redraw_imageView() {
        if (this.CTRL.backgroundP.children.size > 1) this.CTRL.backgroundP.children.remove(1, this.CTRL.backgroundP.children.size)
        this.anotacijeStrani.let { stran ->
            if(stran == null) return@let
            stran.noga.forEach { this.narisi_rectangle(it, Color.BLACK) }
            stran.naloge.forEach { it -> it.forEach { this.narisi_rectangle(it, Color.GREEN) } }
            stran.naslov.forEach { this.narisi_rectangle(it, Color.BLUE) }
            stran.glava.forEach { this.narisi_rectangle(it, Color.BLACK) }
            stran.teorija.forEach { this.narisi_rectangle(it, Color.RED) }
        }
        this.userAnotacije.forEach { this.narisi_rectangle(it, Color.MAGENTA) }
    }

    fun init(zipSlika: Slika) {
        this.data = zipSlika
        this.vseAnotacije = this.ocrService.google(image = zipSlika.img)
        this.init_imageView()
    }

    private fun init_imageView() {
        this.log.info("init: ${this.data}")
        this.anotacijeStrani = this.procesirajOmegoSliko.zdaj(slika = this.data!!, annos = this.vseAnotacije)
        this.CTRL.init(img = this.data!!.img)
        this.redraw_imageView()
    }
}
