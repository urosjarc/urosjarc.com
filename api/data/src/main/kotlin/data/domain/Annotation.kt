package data.domain

import com.google.cloud.vision.v1.EntityAnnotation
import data.extend.xy

data class Annotation(
    val x: Int,
    val y: Int,
    val height: Int,
    val width: Int,
    val text: String,
    val tip: Tip,
) {

    enum class Tip { FOOTER, NALOGA, NASLOV, HEAD, NEZNANO }
}
