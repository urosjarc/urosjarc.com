package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.domain.Kontakt
import si.urosjarc.server.core.repos.KontaktRepo

object KontaktSqlRepo : KontaktRepo, SqlRepo<Kontakt>(ime<Kontakt>()) {
    val oseba_id = reference(Kontakt::oseba_id.name, OsebaSqlRepo.id)
    val data = varchar(Kontakt::data.name, STR_SHORT)
    val tip = varchar(Kontakt::tip.name, STR_SHORT)
}
