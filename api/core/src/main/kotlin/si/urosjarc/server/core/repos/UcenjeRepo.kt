package si.urosjarc.server.core.repos

import kotlinx.serialization.json.JsonElement
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Ucenje

interface UcenjeRepo : DbRepo<Ucenje> {
    fun get_ucence(id_ucitelj: Id<Oseba>): JsonElement
    fun get_ucenec(id_ucenec: Id<Oseba>): JsonElement
}
