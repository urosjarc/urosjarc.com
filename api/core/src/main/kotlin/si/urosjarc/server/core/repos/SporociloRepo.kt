package si.urosjarc.server.core.repos

import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.Repo
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Sporocilo

interface SporociloRepo : Repo<Sporocilo> {
    fun dobi_posiljatelje(id_prejemnika: Id<Oseba>): DomenskiGraf
}
