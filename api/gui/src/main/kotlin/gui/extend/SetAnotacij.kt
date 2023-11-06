package gui.extend

import gui.domain.Anotacija
import gui.domain.Okvir

val Set<Anotacija>.najmanjsiOkvir: Okvir get() = this.okvirji.najmanjsiOkvir

val Set<Anotacija>.okvirji get() = this.map { it.okvir }.toSet()

fun Set<Anotacija>.robVOkvirju(okvir: Okvir): Set<Anotacija> = this.filter { okvir.vsebujeRob(it.okvir) }.toSet()
fun Set<Anotacija>.vOkvirju(okvir: Okvir): Set<Anotacija> = this.filter { okvir.vsebuje(it.okvir) }.toSet()
fun Set<Anotacija>.enakaVrstica(ano: Anotacija): List<Anotacija> = this.filter { ano.okvir.enakaVrstica(it.okvir) }.sortedBy { it.okvir.povprecje.x }

val Set<Anotacija>.matrika: List<List<Anotacija>>
    get(): List<List<Anotacija>> = this.okvirji.matrika.map { vrstica -> vrstica.map { this.vOkvirju(it).first() } }