package gui.extend

import gui.domain.Vektor
import javafx.scene.shape.Rectangle

val Rectangle.start get() = Vektor(x = this.x.toInt(), y = this.y.toInt())
val Rectangle.end get() = Vektor(x = (this.x + this.width).toInt(), y = (this.y + this.height).toInt())
