package si.urosjarc.server.core.base

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.domain.*

@Serializable
data class DomenskiGraf(
    val naloga: MutableMap<Int, Naloga> = mutableMapOf(),
    val status: MutableMap<Int, Status> = mutableMapOf(),
    val tematika: MutableMap<Int, Tematika> = mutableMapOf(),
    val test: MutableMap<Int, Test> = mutableMapOf(),
    val zvezek: MutableMap<Int, Zvezek> = mutableMapOf(),
    val oseba: MutableMap<Int, Oseba> = mutableMapOf(),
    val otroci: MutableMap<String, MutableMap<Int, MutableMap<String, MutableSet<Int>>>> = mutableMapOf(),
    val kontakt_posiljatelja: MutableMap<Int, Kontakt> = mutableMapOf(),
    val kontakt_prejemnika: MutableMap<Int, Kontakt> = mutableMapOf(),
)
