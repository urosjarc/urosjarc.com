package si.urosjarc.server.core.repos

import kotlinx.serialization.Serializable
import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.Repo
import si.urosjarc.server.core.domain.Kontakt
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Sporocilo

interface SporociloRepo : Repo<Sporocilo> {

    @Serializable
    data class DobiPosiljateljeGraf(
        val kontakt_prejemnika: MutableMap<String, Kontakt> = mutableMapOf(),
        val kontakt_posiljatelja: MutableMap<String, Kontakt> = mutableMapOf(),
        val oseba_posiljatelj: MutableMap<String, Oseba> = mutableMapOf()
    )

    fun dobi_posiljatelje(id_prejemnika: Id<Oseba>): DomenskiGraf
}
