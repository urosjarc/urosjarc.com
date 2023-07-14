@file:OptIn(ExperimentalJsExport::class)

import domain.Status
import kotlinx.css.CSSBuilder
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.css.color

object barva {
    val normalna = Color.royalBlue
    val napaka = Color.red
    val opozorilo = Color.orange
    val pravilno = Color.forestGreen
}

@JsExport
fun status_barva(tip: Status.Tip): Color = when (tip) {
    Status.Tip.NEZACETO -> barva.normalna
    Status.Tip.NERESENO -> barva.napaka
    Status.Tip.PRAVILNO -> barva.pravilno
    Status.Tip.NAPACNO -> barva.opozorilo
}

@JsExport
fun status_stil(tip: Status.Tip): CSSBuilder {
    val barva = status_barva(tip)

    return when (tip) {
        Status.Tip.NEZACETO -> CSSBuilder().apply {
            backgroundColor = barva
            color = Color.white
        }

        Status.Tip.NERESENO -> CSSBuilder().apply {
            backgroundColor = barva
            color = Color.white
        }

        Status.Tip.PRAVILNO -> CSSBuilder().apply {
            backgroundColor = barva
            color = Color.white
        }

        Status.Tip.NAPACNO -> CSSBuilder().apply {
            backgroundColor = barva
            color = Color.black
        }
    }
}

@JsExport
fun test_stil(deadline: Int): CSSBuilder {
    val status = if (deadline > 7) Status.Tip.NEZACETO
    else if (deadline > 3) Status.Tip.NAPACNO
    else Status.Tip.NERESENO

    return status_stil(status)
}
