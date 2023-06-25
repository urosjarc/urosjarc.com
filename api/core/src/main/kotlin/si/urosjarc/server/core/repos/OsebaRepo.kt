package si.urosjarc.server.core.repos

import kotlinx.serialization.json.JsonElement
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Kontakt
import si.urosjarc.server.core.domain.Naslov
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Sporocilo

interface OsebaRepo : DbRepo<Oseba>
interface KontaktRepo : DbRepo<Kontakt>
interface NaslovRepo : DbRepo<Naslov>
interface SporociloRepo : DbRepo<Sporocilo> {
    fun get_posiljatelje(id_prejemnika: Id<Oseba>): JsonElement
}
