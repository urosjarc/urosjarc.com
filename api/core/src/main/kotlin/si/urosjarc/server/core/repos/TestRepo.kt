package si.urosjarc.server.core.repos

import kotlinx.serialization.json.JsonElement
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Status
import si.urosjarc.server.core.domain.Test

interface TestRepo : DbRepo<Test>
interface StatusRepo : DbRepo<Status> {
    fun get_statuse(id_osebe: Id<Oseba>): JsonElement
}
