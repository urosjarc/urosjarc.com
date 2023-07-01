package si.urosjarc.server.core.repos

import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.Repo
import si.urosjarc.server.core.domain.Oseba

interface OsebaRepo : Repo<Oseba> {
    fun dobi_kontakte_in_sporocila(id: Id<Oseba>): DomenskiGraf

    fun dobi_tests_in_statuse(id_osebe: Id<Oseba>): DomenskiGraf

    fun dobi_ucenje(id: Id<Oseba>): DomenskiGraf
    fun dobi_naslov(id: Id<Oseba>): DomenskiGraf
}
