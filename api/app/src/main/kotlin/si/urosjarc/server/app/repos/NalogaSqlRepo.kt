package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Naloga
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.NalogaRepo

object NalogaSqlRepo : NalogaRepo, SqlRepo<Naloga>(ime<Naloga>()) {
    val resitev = varchar(Naloga::resitev.name, STR_LONG)
    val vsebina = varchar(Naloga::vsebina.name, STR_LONG)
    val tematika_id = reference(Naloga::tematika_id.name, TematikaSqlRepo.id)
}
