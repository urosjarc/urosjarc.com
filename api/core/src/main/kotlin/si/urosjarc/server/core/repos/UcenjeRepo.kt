package si.urosjarc.server.core.repos

import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.Repo
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Ucenje

interface UcenjeRepo : Repo<Ucenje> {
    fun dobi_ucence(id_ucitelja: Id<Oseba>): DomenskiGraf
    fun dobi_ucitelje(id_ucenca: Id<Oseba>): DomenskiGraf
}
