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

        Anotacija.Tip.entries.forEach {
            val menuItem1 = MenuItem(it.name)
            menuItem1.userData = it
            this.contextMenu.items.add(menuItem1)
        }


        this.imageView_bufferedImage_Controller.let { ctrl ->
            ctrl.self.setOnMousePressed {
                this.dragStart = this.vektor(it)
            }

            ctrl.self.setOnMouseReleased {
                this.dragEnd = this.vektor(it)
                contextMenu.show(ctrl.self, it.screenX, it.screenY)
            }

            ctrl.self.setOnMouseDragged {
                val v = this.vektor(it)
                val rec = Rectangle(this.dragStart!!.x, this.dragStart!!.y, v.x - this.dragStart!!.x, v.y - this.dragStart!!.y)
                rec.fill = null
                rec.stroke = Color.RED
                rec.strokeWidth = 2.0
                if (ctrl.backgroundP.children.size > 1) ctrl.backgroundP.children.remove(2, ctrl.backgroundP.children.size)
                ctrl.backgroundP.children.add(rec)
            }

            this.resetirajB.setOnAction {
                ctrl.backgroundP.children.remove(2, ctrl.backgroundP.children.size)
            }

            this.contextMenu.setOnAction {
                ctrl.backgroundP.children.remove(2, ctrl.backgroundP.children.size)
                val tip = (it.target as MenuItem).userData as Anotacija.Tip
                println(tip)
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

        img.drawSlikaAnnotations(this.anotacijeStrani!!)

        this.imageView_bufferedImage_Controller.init(img = img)
    }

}
