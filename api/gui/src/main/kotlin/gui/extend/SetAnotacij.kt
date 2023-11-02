package gui.extend

import gui.domain.Anotacija
import gui.domain.Okvir

val Set<Anotacija>.okvirji get() = this.map { it.okvir }.toSet()

fun Set<Anotacija>.vOkvirju(okvir: Okvir): Set<Anotacija> = this.filter { okvir.vsebuje(it.okvir) }.toSet()

val Set<Anotacija>.matrika: List<List<Anotacija>>
    get(): List<List<Anotacija>> = this.okvirji.matrika.map { vrstica -> vrstica.map { this.vOkvirju(it).first() } }
