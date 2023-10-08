package data.domain

import kotlinx.serialization.Serializable

@Serializable
data class SlikaAnotations(
    val footer: MutableList<Annotation> = mutableListOf(),
    val naloge: MutableList<MutableList<Annotation>> = mutableListOf(),
    var naslov: MutableList<Annotation> = mutableListOf(),
    val head: MutableList<Annotation> = mutableListOf(),
    val teorija: MutableList<Annotation> = mutableListOf()
) {
    fun init() {
        this.footer.forEach { it.tip = Annotation.Tip.FOOTER }
        this.naloge.forEach { it.forEach { it.tip = Annotation.Tip.NALOGA } }
        this.naslov.forEach { it.tip = Annotation.Tip.NASLOV }
        this.head.forEach { it.tip = Annotation.Tip.HEAD }
        this.teorija.forEach { it.tip = Annotation.Tip.TEORIJA }
    }
}
