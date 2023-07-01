package si.urosjarc.server.core.base

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.extend.ime

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
) {
    inline fun <reified T : Entiteta<T>, reified K : Entiteta<K>> vsi_otroci(
        stars: T, otrociCB: (domenskiGraf: DomenskiGraf) -> MutableMap<Int, K>
    ): List<K?> {
        val stars_ime = ime<T>()
        val otrok_ime = ime<K>()
        val property = otrociCB(this)
        return otroci.get(stars_ime)?.get(stars.id.value)?.get(otrok_ime)?.map { property[it] } ?: listOf()
    }
}

fun main() {
    val dm = DomenskiGraf()
    val oseba = Entiteta.nakljucni<Oseba>()
    dm.vsi_otroci(oseba) { it.status }
}
