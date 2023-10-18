package gui.domain

import java.awt.image.BufferedImage

data class Odsek(
    val y: Int,
    val img: BufferedImage,
    val tip: Anotacija.Tip
)
