package data.extend

import data.domain.Vektor
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
        x = xPercent * img.width,
        y = yPercent * img.height
    )
}
