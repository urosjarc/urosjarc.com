package gui.extend

import gui.domain.Anotacija
import gui.domain.Okvir

val List<Anotacija>.okvirji get() = this.map { it.okvir }

fun List<Anotacija>.vOkvirju(okvir: Okvir): List<Anotacija> = this.filter { okvir.vsebuje(it.okvir) }
