package si.urosjarc.server.core.base

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.domain.*


@Serializable
data class DomenskiGraf(
    val naloga: MutableMap<String, Naloga> = mutableMapOf(),
    val status: MutableMap<String, Status> = mutableMapOf(),
    val tematika: MutableMap<String, Tematika> = mutableMapOf(),
    val test: MutableMap<String, Test> = mutableMapOf(),
    val zvezek: MutableMap<String, Zvezek> = mutableMapOf(),
    val oseba: MutableMap<String, Oseba> = mutableMapOf(),
)
