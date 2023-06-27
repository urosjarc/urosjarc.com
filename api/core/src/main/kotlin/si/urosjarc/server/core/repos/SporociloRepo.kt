package si.urosjarc.server.core.repos

import kotlinx.serialization.json.JsonElement
import si.urosjarc.server.core.base.Repo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Sporocilo

interface SporociloRepo : Repo<Sporocilo> {
    fun get_posiljatelje(id_prejemnika: Id<Oseba>): JsonElement
}
