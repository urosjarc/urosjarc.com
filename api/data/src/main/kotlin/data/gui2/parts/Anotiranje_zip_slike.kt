package data.gui2.parts

import data.domain.Anotacija
import data.domain.AnotacijeStrani
import data.domain.Vektor
import data.domain.ZipSlika
import data.extend.*
import data.gui2.elements.ImageView_BufferedImage
import data.services.LogService
import data.services.OcrService
import data.use_cases.Procesiraj_omego_sliko
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Anotiranje_zip_slike : KoinComponent {

    private val log by this.inject<LogService>()
    private val procesirajOmegoSliko by this.inject<Procesiraj_omego_sliko>()
    private val ocrService by this.inject<OcrService>()

    @FXML
    lateinit var resetirajB: Button

    val contextMenu = ContextMenu()
    var dodaneAnotacije = mutableListOf<Anotacija>()
    var anotacijeStrani: AnotacijeStrani? = null
    var data: ZipSlika? = null
    var dragStart: Vektor? = null
    var dragEnd: Vektor? = null
    var dragRectangle: Rectangle? = null

    @FXML
    lateinit var imageView_bufferedImage_Controller: ImageView_BufferedImage

    private fun vektor(mouseEvent: MouseEvent): Vektor {
        val image = this.imageView_bufferedImage_Controller.self.image
        val img = this.data!!.img
        val widthR = img.width / image.width
        val heightR = img.height / image.height
        return Vektor(x = mouseEvent.x * widthR, y = mouseEvent.y * heightR)
    }

    @FXML
    fun initialize() {
        println("init Anotiranje_zip_slike")

        /**
         * Dodajanje tipov anotacij v context menu slike
         */
        Anotacija.Tip.entries.forEach {
            val menuItem1 = MenuItem(it.name)
            menuItem1.userData = it
            this.contextMenu.items.add(menuItem1)
        }


        this.imageView_bufferedImage_Controller.let { ctrl ->

            /**
             * Shrani informacijo kje se je drag zacel
             */
            ctrl.self.setOnMousePressed {
                this.dragStart = this.vektor(it)
            }

            /**
             * Shrani kje se je drag koncal...
             */
            ctrl.self.setOnMouseReleased {
                this.dragEnd = this.vektor(it)
                contextMenu.show(ctrl.self, it.screenX, it.screenY)
            }

            /**
             * Procesiraj ko se drag dogaja
             */
            ctrl.self.setOnMouseDragged {
                ctrl.backgroundP.children.remove(this.dragRectangle)

                val v = this.vektor(it)
                this.dragRectangle = Rectangle(
                    this.dragStart!!.x,
                    this.dragStart!!.y,
                    v.x - this.dragStart!!.x,
                    v.y - this.dragStart!!.y
                ).apply {
                    this.fill = null
                    this.stroke = Color.RED
                    this.strokeWidth = 2.0
                }

                ctrl.backgroundP.children.add(this.dragRectangle)
            }

            /**
             * Ce uporabnik klikne reset pobrisi vse anotacije ustvarjene od uporabnika
             */
            this.resetirajB.setOnAction { this.redraw_imageView() }

            /**
             * Ko se izbere context menu shrani izbrane anotacije in jih prikazi na strani.
             */
            this.contextMenu.setOnAction {
                this.redraw_imageView()
                val tip = (it.target as MenuItem).userData as Anotacija.Tip
                println(tip)
            }
        }
    }

    private fun narisi_rectangle(rec: Rectangle) {
        this.imageView_bufferedImage_Controller.backgroundP.children.add(rec)
    }

    private fun redraw_imageView() {
        this.imageView_bufferedImage_Controller.let { ctrl ->
            if (ctrl.backgroundP.children.size > 1) {
                ctrl.backgroundP.children.remove(1, ctrl.backgroundP.children.size)
            }
            this.anotacijeStrani.let { stran ->
                stran!!.noga.forEach { this.narisi_rectangle(it.rectangle(color = Color.BLACK)) }
                stran.naloge.forEach { it -> it.forEach { this.narisi_rectangle(it.rectangle(Color.GREEN)) } }
                stran.naslov.forEach { this.narisi_rectangle(it.rectangle(Color.BLUE)) }
                stran.glava.forEach { this.narisi_rectangle(it.rectangle(Color.BLACK)) }
                stran.teorija.forEach { this.narisi_rectangle(it.rectangle(Color.RED)) }
            }
        }
    }

    fun init(zipSlika: ZipSlika) {
        this.data = zipSlika
        this.init_imageView()
    }

    private fun init_imageView() {
        this.log.info("init: ${this.data}")
        val img = this.data!!.img.copy()
        val anotacije = this.ocrService.google(image = img)

        this.dodaneAnotacije.clear()
        this.anotacijeStrani = this.procesirajOmegoSliko.zdaj(img = img, annos = anotacije)
        this.log.info("init anotacije: $anotacijeStrani")

        this.redraw_imageView()
        this.imageView_bufferedImage_Controller.init(img = img)
    }
}
