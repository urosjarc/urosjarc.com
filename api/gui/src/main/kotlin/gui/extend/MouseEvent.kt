package gui.extend

import gui.domain.Vektor
import javafx.scene.input.MouseEvent

val MouseEvent.vektor get() = Vektor(x= this.x.toInt(), y= this.y.toInt())
