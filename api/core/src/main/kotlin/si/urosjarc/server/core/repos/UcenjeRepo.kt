package si.urosjarc.server.core.repos

import si.urosjarc.server.core.base.DomainMap
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.Repo
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Ucenje

interface UcenjeRepo : Repo<Ucenje> {
    fun get_ucence(id_ucitelja: Id<Oseba>): DomainMap
    fun get_ucitelje(id_ucenca: Id<Oseba>): DomainMap
}
