package gui.app.elements

import gui.base.Opazovan
import gui.domain.Okvir
import gui.domain.Vektor
import gui.extend.end
import gui.extend.inputStream
import gui.extend.start
import javafx.fxml.FXML
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.koin.core.component.KoinComponent
import java.awt.image.BufferedImage


abstract class ImageView_BufferedImage_UI : KoinComponent {

    @FXML
    lateinit var self: ImageView

    @FXML
    lateinit var backgroundP: Pane

    @FXML
    lateinit var stackPane: StackPane

    @FXML
    lateinit var scrollPane: ScrollPane
}

class ImageView_BufferedImage : ImageView_BufferedImage_UI() {
    var sirokaSlika = false
    var visina = 1000.0
    var sirina = 900.0
    var zoom = Opazovan<Double>()

    @FXML
    fun initialize() {
        this.self.setOnScroll { if (it.isControlDown) this.popravi_velikost(dy = it.deltaY) }
    }

    fun init(slika: BufferedImage) {
        this.sirokaSlika = slika.width > slika.height
        this.self.image = Image(slika.inputStream)
        this.pobrisi_ozadje()
        this.popravi_velikost()
    }

    private fun popravi_velikost(dy: Double = 0.0) {
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

    fun pobrisi_ozadje() {
        if (this.backgroundP.children.size > 1) this.backgroundP.children.remove(1, this.backgroundP.children.size)
    }

    fun narisi_okvir(okvir: Okvir, color: Color, round: Int) {
        val rec = this.vRectangle(okvir = okvir, color = color, round = round)
        this.backgroundP.children.add(rec)
    }

    fun vRectangle(okvir: Okvir, color: Color = Color.BLACK, round: Int): Rectangle = Okvir(
        start = this.mapiraj(v = okvir.start, noter = true),
        end = this.mapiraj(v = okvir.end, noter = true)
    ).vRectangle(color = color, round = round).also { rec -> rec.setOnScroll { if (it.isControlDown) this.popravi_velikost(dy = it.deltaY) } }

    fun vOkvir(r: Rectangle): Okvir = Okvir(start = this.mapiraj(v = r.start, noter = false), end = this.mapiraj(v = r.end, noter = false))

    fun mapiraj(v: Vektor, noter: Boolean): Vektor {
        val img = this.self.image
        val rx = img.width / this.self.fitWidth
        val ry = img.height / this.self.fitHeight
        return Vektor(
            x = (if (noter) v.x / rx else v.x * rx).toInt(),
            y = (if (noter) v.y / ry else v.y * ry).toInt(),
        )
    }


}
