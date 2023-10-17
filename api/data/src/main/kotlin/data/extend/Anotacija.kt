package data.extend

import data.domain.Anotacija
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

fun Anotacija.rectangle(color: Color): Rectangle {
    val r = Rectangle(this.x, this.y, this.width, this.height)
    r.fill = null
    r.stroke = color
    r.strokeWidth = 2.0
    return r
}
