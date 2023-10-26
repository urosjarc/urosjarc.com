package gui.domain

import gui.extend.okvirji
import gui.extend.vsebujejo
import kotlinx.serialization.Serializable

@Serializable
class Stran(val okvir: Okvir, val anotacije: Set<Anotacija>) {
    var naslov = mutableSetOf<Okvir>()
    val teorija = mutableSetOf<Okvir>()
    val naloge = mutableSetOf<Okvir>()
    val podnaloge = mutableSetOf<Okvir>()
    val noga = mutableSetOf<Okvir>()
    val dodatno = mutableSetOf<Okvir>()

    companion object {
        val PRAZNA get() = Stran(okvir = Okvir.PRAZEN, anotacije = setOf())
    }

    val okvirji get() = this.anotacije.okvirji + this.naslov + this.teorija + this.naloge + this.podnaloge + this.noga + this.dodatno
    fun odstrani(okvirji: Set<Okvir>) {
        this.naslov.removeAll(okvirji)
        this.teorija.removeAll(okvirji)
        this.naloge.removeAll(okvirji)
        this.podnaloge.removeAll(okvirji)
        this.noga.removeAll(okvirji)
        this.dodatno.removeAll(okvirji)
    }

    fun dodaj(okvirji: Set<Okvir>, tip: Anotacija.Tip) {
        this.odstrani(okvirji = okvirji)
        when (tip) {
            Anotacija.Tip.NEZNANO -> {}
            Anotacija.Tip.NASLOV -> this.naslov.addAll(okvirji)
            Anotacija.Tip.TEORIJA -> this.teorija.addAll(okvirji)
            Anotacija.Tip.NALOGA -> this.naloge.addAll(okvirji)
            Anotacija.Tip.PODNALOGA -> this.podnaloge.addAll(okvirji)
            Anotacija.Tip.NOGA -> this.noga.addAll(okvirji)
            Anotacija.Tip.DODATNO -> this.noga.addAll(okvirji)
        }
    }

    fun izberi(okvirji: Set<Okvir>, tip: Anotacija.Tip) = this.dodaj(okvirji = okvirji.intersect(this.okvirji), tip = tip)

    fun okvirjiV(vektor: Vektor) = this.okvirji.vsebujejo(vektor = vektor)
}
