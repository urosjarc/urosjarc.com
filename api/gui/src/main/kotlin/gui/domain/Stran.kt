package gui.domain

class Stran(val okvir: Okvir, val anotacije: List<Anotacija>) {

    var glava = mutableListOf<Okvir>()
    var naslov = mutableListOf<Okvir>()
    val teorija = mutableListOf<Okvir>()
    val naloge = mutableListOf<Okvir>()
    val noga = mutableListOf<Okvir>()

    fun odstrani(okvirji: Collection<Okvir>) {
        this.glava.removeAll(okvirji)
        this.naslov.removeAll(okvirji)
        this.teorija.removeAll(okvirji)
        this.naloge.removeAll(okvirji)
        this.noga.removeAll(okvirji)
    }

    fun dodaj(okvirji: Collection<Okvir>, tip: Anotacija.Tip) {
        when (tip) {
            Anotacija.Tip.NEZNANO -> {}
            Anotacija.Tip.GLAVA -> this.glava.addAll(okvirji)
            Anotacija.Tip.NASLOV -> this.naslov.addAll(okvirji)
            Anotacija.Tip.TEORIJA -> this.teorija.addAll(okvirji)
            Anotacija.Tip.NALOGA -> this.naloge.addAll(okvirji)
            Anotacija.Tip.NOGA -> this.noga.addAll(okvirji)
        }
    }
}
