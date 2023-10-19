package gui.app.elements;

import gui.base.Opazovan
import gui.domain.Anotacija
import gui.domain.Vektor
import gui.extend.inputStream
import javafx.fxml.FXML
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.koin.core.component.KoinComponent
import java.awt.image.BufferedImage

class ImageView_BufferedImage : KoinComponent {
    @FXML
    lateinit var backgroundP: Pane

    @FXML
    lateinit var self: ImageView

    var sirokaSlika = false

    var zoom = Opazovan(0.0)
    var visina = 900.0
    var sirina = 300.0

    fun init(img: BufferedImage, sirokaSlika: Boolean = false) {
        this.sirokaSlika = sirokaSlika
        this.self.image = Image(img.inputStream())
        this.pobrisiOzadje()
        this.popraviVelikost(0.0)
    }

    fun popraviVelikost(dy: Double) {
        val img = this.self.image
        val ratio = img.height / img.width
        val v = (if (sirokaSlika) sirina else visina) + dy

        this.backgroundP.let {
            if (sirokaSlika) {
                it.maxHeight = v
                it.minHeight = v
                it.maxWidth = v / ratio
                it.minWidth = v / ratio
            } else {
                it.maxWidth = v
                it.minWidth = v
                it.maxHeight = v * ratio
                it.minHeight = v * ratio
            }
        }
        this.self.let {
            if (sirokaSlika) {
                it.fitHeight = v
                it.fitWidth = v / ratio
                sirina = v
            } else {
                it.fitWidth = v
                it.fitHeight = v * ratio
                visina = v
            }
        }

        zoom.value = dy
    }

    fun kvadratAnotacije(ano: Anotacija, color: Color = Color.BLACK): Rectangle {
        val v = Vektor(x = ano.x, y = ano.y)
        val img = this.self.image
        val rx = img.width / this.self.fitWidth
        val ry = img.height / this.self.fitHeight
        val rec = Rectangle(v.x / rx, v.y / ry, ano.width / rx, ano.height / ry)
        rec.fill = null
        rec.stroke = color
        rec.strokeWidth = 2.0
        return rec
    }

    fun pobrisiOzadje() {
        if (this.backgroundP.children.size > 1) this.backgroundP.children.remove(1, this.backgroundP.children.size)
    }

    @FXML
    fun initialize() {
        println("init ImageView_ZipSlika")
        this.self.setOnScroll {
            this.popraviVelikost(dy = it.deltaY)
        }

    }

    @FXML
    fun clicked() {
        println("clicked")
    }
}
