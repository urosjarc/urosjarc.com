package gui.extend

import gui.domain.Anotacija
import gui.domain.Okvir
import gui.domain.Vektor

val Set<Anotacija>.okvirji get() = this.map { it.okvir }.toSet()

fun Set<Anotacija>.vOkvirju(okvir: Okvir): Set<Anotacija> = this.filter { okvir.vsebuje(it.okvir) }.toSet()
