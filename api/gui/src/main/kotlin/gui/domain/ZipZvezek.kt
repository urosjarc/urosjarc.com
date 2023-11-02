package gui.domain

import core.base.AnyId
import java.awt.image.BufferedImage

data class ZipZvezek(
    val ime: String,
    val id: AnyId = AnyId(),
    val tematike: MutableList<ZipTematika> = mutableListOf()
)

data class ZipTematika(
    val ime: String,
    val id: AnyId = AnyId(),
    val naloge: MutableList<ZipNaloga> = mutableListOf(),
    val teorije: MutableList<ZipTeorija> = mutableListOf()
)

data class ZipTeorija(
    val img: BufferedImage,
    val id: AnyId = AnyId(),
)

data class ZipNaloga(
    val text: String,
    val img: BufferedImage,
    val id: AnyId = AnyId(),
    val podnaloge: MutableList<ZipPodnaloga> = mutableListOf(),
    val dodatno: MutableList<BufferedImage> = mutableListOf()
)

data class ZipPodnaloga(
    val img: BufferedImage,
    val id: AnyId = AnyId(),
)
