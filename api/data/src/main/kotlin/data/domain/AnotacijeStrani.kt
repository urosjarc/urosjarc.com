package data.domain

import kotlinx.serialization.Serializable

@Serializable
data class AnotacijeStrani(
    val glava: MutableList<Anotacija> = mutableListOf(),
    var naslov: MutableList<Anotacija> = mutableListOf(),
    val teorija: MutableList<Anotacija> = mutableListOf(),
    val naloge: MutableList<MutableList<Anotacija>> = mutableListOf(),
    val noga: MutableList<Anotacija> = mutableListOf(),
) {
    fun init() {
        this.noga.forEach { it.tip = Anotacija.Tip.FOOTER }
        this.naloge.forEach { it.forEach { it.tip = Anotacija.Tip.NALOGA } }
        this.naslov.forEach { it.tip = Anotacija.Tip.NASLOV }
        this.glava.forEach { it.tip = Anotacija.Tip.HEAD }
        this.teorija.forEach { it.tip = Anotacija.Tip.TEORIJA }
    }
}
