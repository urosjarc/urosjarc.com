package gui.app.elements

import gui.base.Opazovan
import gui.domain.Anotacija
import gui.domain.Odsek
import gui.domain.Okvir
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

    fun kvadrat(okvir: Okvir, color: Color = Color.BLACK): Rectangle {
        val img = this.self.image
        val rx = img.width / this.self.fitWidth
        val ry = img.height / this.self.fitHeight

        val rec = Rectangle(okvir.x0 / rx, okvir.y0 / ry, okvir.x1 / rx, okvir.y1 / ry)
        rec.fill = null
        rec.stroke = color
        rec.strokeWidth = 2.0

        return rec
    }

    fun anotacija(r: Rectangle, text: String, tip: Anotacija.Tip): Anotacija {
        val img = this.self.image
        val rx = img.width / this.self.fitWidth
        val ry = img.height / this.self.fitHeight
        return Anotacija(
            x = (r.x * rx).toInt(),
            y = (r.y * ry).toInt(),
            width = (r.width * rx).toInt(),
            height = (r.height * ry).toInt(),
            text = text,
            tip = tip
        )
    }

    fun narisi_anotacijo(ano: Anotacija, color: Color) {
        val okvir = Okvir(x0 = ano.x, y0 = ano.y, x1 = ano.x_max, y1 = ano.y_max)
        val rec = this.kvadrat(okvir = okvir, color = color)
        this.backgroundP.children.add(rec)
    }

    fun narisi_odsek(odsek: Odsek, color: Color) {
        val okvir = Okvir(
            x0 = odsek.pozicija.x,
            y0 = odsek.pozicija.y,
            x1 = odsek.pozicija.x + odsek.sirina,
            y1 = odsek.pozicija.y + odsek.visina
        )
        val rec = this.kvadrat(okvir = okvir, color = color)
        this.backgroundP.children.add(rec)
    }
}
