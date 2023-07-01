package si.urosjarc.server.core.base

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.extend.ime

@Serializable
class DomenskiGraf {
    val naloga = mutableMapOf<Int, Naloga>()
    val status = mutableMapOf<Int, Status>()
    val tematika = mutableMapOf<Int, Tematika>()
    val test = mutableMapOf<Int, Test>()
    val zvezek = mutableMapOf<Int, Zvezek>()
    val oseba = mutableMapOf<Int, Oseba>()
    val otroci = mutableMapOf<String, MutableMap<Int, MutableMap<String, MutableSet<Int>>>>()
    val kontakt_posiljatelja = mutableMapOf<Int, Kontakt>()
    val kontakt_prejemnika = mutableMapOf<Int, Kontakt>()

    inline fun <reified P : Entiteta<P>, reified C : Entiteta<C>> vsi_otroci(
        stars: P,
        otrociCB: (domenskiGraf: DomenskiGraf) -> MutableMap<Int, C>
    ): MutableList<C> {
        val stars_ime = ime<P>()
        val otrok_ime = ime<C>()
        val property = otrociCB(this)
        val defaultList: MutableList<C> = mutableListOf()

        otroci
            .get(stars_ime)
            ?.get(stars.id.value)
            ?.get(otrok_ime)
            ?.forEach {
                property[it]?.let { it1 -> defaultList.add(it1) }
            }

        return defaultList
    }
}
