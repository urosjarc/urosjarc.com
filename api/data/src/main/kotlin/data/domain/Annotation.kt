package data.domain

import kotlinx.serialization.Serializable

@Serializable
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
