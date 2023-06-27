package si.urosjarc.server.core.repos

import kotlinx.serialization.json.JsonElement
import si.urosjarc.server.core.base.Repo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Status

interface StatusRepo : Repo<Status> {
    fun get_statuse(id_osebe: Id<Oseba>): JsonElement
}
