package data.domain

import com.google.cloud.vision.v1.EntityAnnotation
import java.awt.image.BufferedImage

data class Annotation(
    val ocr: EntityAnnotation,
    var img: BufferedImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB),
    val tip: Tip,
) {
    companion object {
        fun map(annos: Collection<EntityAnnotation>, tip: Tip): MutableList<Annotation> {
            return annos.map { Annotation(ocr = it, tip = tip) }.toMutableList()
        }
    }

    enum class Tip { FOOTER, NALOGA, NASLOV, HEAD }
}
