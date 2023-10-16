package data.domain

import java.awt.image.BufferedImage

data class ZipSlika(
    val img: BufferedImage,
    val index: Int,
    val size: Int
) {
    override fun toString(): String {
        return "ZipSlika(index=$index, size=$size, img=${img.width}x${img.height})"
    }
}
