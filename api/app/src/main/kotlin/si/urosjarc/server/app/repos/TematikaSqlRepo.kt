package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Tematika
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.TematikaRepo

object TematikaSqlRepo : TematikaRepo, SqlRepo<Tematika>(ime<Tematika>()) {
    val naslov = varchar(Tematika::naslov.name, STR_MEDIUM)
    val zvezek_id = reference(Tematika::zvezek_id.name, ZvezekSqlRepo.id)
}
