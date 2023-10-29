package gui.extend

import gui.domain.Vektor
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import java.awt.image.BufferedImage

fun ImageView.mousePosition(img: BufferedImage, event: MouseEvent): Vektor {
    val r = img.height.toDouble() / img.width
    val h = this.fitHeight
    val w = h / r

    val xPercent: Double = event.x / w
    val yPercent: Double = event.y / h

    return Vektor(
        x = (xPercent * img.width).toInt(),
        y = (yPercent * img.height).toInt()
    )
}
