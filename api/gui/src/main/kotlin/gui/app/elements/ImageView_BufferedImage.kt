package gui.app.elements

import gui.base.Opazovan
import gui.domain.Anotacija
import gui.domain.Odsek
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

abstract class ImageView_BufferedImage_UI : KoinComponent {

    @FXML
    lateinit var self: ImageView

    @FXML
    lateinit var backgroundP: Pane
}

class ImageView_BufferedImage : ImageView_BufferedImage_UI() {
    var sirokaSlika = false
    var visina = 1000.0
    var sirina = 900.0
    var zoom = Opazovan<Double>()

    @FXML
    fun initialize() {
        println("init ImageView_ZipSlika")
        this.self.setOnScroll {
            this.popraviVelikost(dy = it.deltaY)
        }
    }

    fun init(img: BufferedImage, sirokaSlika: Boolean = false) {
        this.sirokaSlika = sirokaSlika
        this.self.image = Image(img.inputStream())
        this.pobrisiOzadje()
        this.popraviVelikost(0.0)
    }

    fun pobrisiOzadje() {
        if (this.backgroundP.children.size > 1) this.backgroundP.children.remove(1, this.backgroundP.children.size)
    }

    private fun popraviVelikost(dy: Double) {
        val img = this.self.image
        val ratio = img.height / img.width
        val v = (if (sirokaSlika) sirina else visina) + dy

        this.backgroundP.let {
            if (sirokaSlika) {
                it.maxWidth = v
                it.minWidth = v
                it.maxHeight = v * ratio
                it.minHeight = v * ratio
            } else {
                it.maxHeight = v
                it.minHeight = v
                it.maxWidth = v / ratio
                it.minWidth = v / ratio
            }
        }
        this.self.let {
            if (sirokaSlika) {
                it.fitWidth = v
                it.fitHeight = v * ratio
                sirina = v
            } else {
                it.fitHeight = v
                it.fitWidth = v / ratio
                visina = v
            }
        }

        zoom.value = dy
    }

    fun kvadratAnotacije(ano: Anotacija, color: Color = Color.BLACK): Rectangle {
        val img = this.self.image
        val rx = img.width / this.self.fitWidth
        val ry = img.height / this.self.fitHeight

        val v = Vektor(x = ano.x, y = ano.y)
        val rec = Rectangle(v.x / rx, v.y / ry, ano.width / rx, ano.height / ry)
        rec.fill = null
        rec.stroke = color
        rec.strokeWidth = 2.0

        return rec
    }

    fun anotacijaKvadrata(r: Rectangle, text: String, tip: Anotacija.Tip): Anotacija {
        val img = this.self.image
        val rx = img.width / this.self.fitWidth
        val ry = img.height / this.self.fitHeight
        return Anotacija(x = r.x * rx, y = r.y * ry, width = r.width * rx, height = r.height * ry, text = text, tip = tip)
    }

    fun narisi_anotacijo(ano: Anotacija, color: Color) {
        val rec = this.kvadratAnotacije(ano = ano, color = color)
        this.backgroundP.children.add(rec)
    }
    fun narisi_odsek(odsek: Odsek, color: Color) {
        val ano = Anotacija(x= odsek.x.toDouble(), y= odsek.y.toDouble(), width = odsek.sirina, height = odsek.visina, text = "", tip = Anotacija.Tip.NEZNANO)
        val rec = this.kvadratAnotacije(ano = ano, color = color)
        this.backgroundP.children.add(rec)
    }
}
