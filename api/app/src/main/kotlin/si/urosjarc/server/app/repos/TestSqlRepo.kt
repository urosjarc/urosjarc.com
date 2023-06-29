package si.urosjarc.server.app.repos

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Test
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.TestRepo

object TestSqlRepo : TestRepo, SqlRepo<Test>(ime<Test>()) {
    val naslov = varchar(Test::naslov.name, STR_MEDIUM)
    val podnaslov = varchar(Test::podnaslov.name, STR_MEDIUM)
    val deadline = date(Test::deadline.name)
    val oseba_id = reference(Test::oseba_id.name, OsebaSqlRepo.id)
}
