package si.urosjarc.server.core.repos

import kotlinx.serialization.json.JsonElement
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Ucenje

interface UcenjeRepo : DbRepo<Ucenje> {
    fun get_ucence(id_ucitelja: Id<Oseba>): JsonElement
    fun get_ucitelje(id_ucenca: Id<Oseba>): JsonElement
}
