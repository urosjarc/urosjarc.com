package gui.domain

import java.awt.image.BufferedImage

data class Odsek(
    val x: Int,
    val y: Int,
    var anotacije: List<Anotacija>,
    val img: BufferedImage,
)
