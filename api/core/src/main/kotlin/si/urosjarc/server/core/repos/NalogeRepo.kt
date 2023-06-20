package si.urosjarc.server.core.repos

import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Naloga

interface NalogeRepo {
    fun get(): List<Naloga>
    fun post(naloga: Naloga)
    fun put(naloga: Naloga)
    fun delete(id: Id<Naloga>)
}
