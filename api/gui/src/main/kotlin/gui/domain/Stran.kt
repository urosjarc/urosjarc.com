package gui.domain

data class Stran(
    val slika: Slika,
    val anotacije: List<Anotacija>,
    var glava: MutableList<Anotacija> = mutableListOf(),
    var naslov: MutableList<Anotacija> = mutableListOf(),
    val teorija: MutableList<Anotacija> = mutableListOf(),
    val naloge: MutableList<MutableList<Anotacija>> = mutableListOf(),
    val noga: MutableList<Anotacija> = mutableListOf(),
) {
    val visina get() = this.slika.img.height.toDouble()
    val sirina get() = this.slika.img.width.toDouble()

    fun init() {
        this.noga.forEach { it.tip = Anotacija.Tip.FOOTER }
        this.naloge.forEach { it.forEach { it.tip = Anotacija.Tip.NALOGA } }
        this.naslov.forEach { it.tip = Anotacija.Tip.NASLOV }
        this.glava.forEach { it.tip = Anotacija.Tip.HEAD }
        this.teorija.forEach { it.tip = Anotacija.Tip.TEORIJA }
    }

    fun dodaj(ano: List<Anotacija>, tip: Anotacija.Tip) {
        when (tip) {
            Anotacija.Tip.NEZNANO -> {}
            Anotacija.Tip.HEAD -> this.glava.addAll(ano)
            Anotacija.Tip.NASLOV -> this.naslov.addAll(ano)
            Anotacija.Tip.TEORIJA -> this.teorija.addAll(ano)
            Anotacija.Tip.NALOGA -> this.naloge.add(ano.toMutableList())
            Anotacija.Tip.FOOTER -> this.noga.addAll(ano)
        }
        this.init()
    }

    fun odstrani(ano: List<Anotacija>) {
        ano.forEach {
            when (it.tip) {
                Anotacija.Tip.NEZNANO -> {}
                Anotacija.Tip.HEAD -> this.glava.remove(it)
                Anotacija.Tip.NASLOV -> this.naslov.remove(it)
                Anotacija.Tip.TEORIJA -> this.teorija.remove(it)
                Anotacija.Tip.NALOGA -> this.naloge.forEach { lit -> lit.remove(it) }
                Anotacija.Tip.FOOTER -> this.noga.remove(it)
            }
        }
        this.init()
    }

    override fun toString(): String {
        return "AnotacijeStrani(naloge=${this.naloge.size}, noga=${this.noga.size}, naslov=${this.naslov.size}, glava=${this.glava.size}, teorija=${this.teorija.size})"
    }
}
