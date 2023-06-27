package si.urosjarc.server.app.repos

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Test
import si.urosjarc.server.core.extends.name
import si.urosjarc.server.core.repos.TestRepo

object TestSqlRepo : TestRepo, SqlRepo<Test>(name<Test>()) {
    val naslov = varchar(Test::naslov.name, STR_MEDIUM)
    val podnaslov = varchar(Test::podnaslov.name, STR_MEDIUM)
    val deadline = date(Test::deadline.name)
    val oseba_id = reference(Test::oseba_id.name, OsebaSqlRepo.id)
    override fun map(obj: Test, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[naslov] = obj.naslov
        any[podnaslov] = obj.podnaslov
        any[deadline] = obj.deadline.toJavaLocalDate()
        any[oseba_id] = obj.oseba_id.value
    }

    override fun resultRow(R: ResultRow): Test {
        val deadline = R[deadline]
        return Test(
            id = Id(R[id]),
            naslov = R[naslov],
            podnaslov = R[podnaslov],
            oseba_id = Id(R[oseba_id]),
            deadline = deadline.toKotlinLocalDate()
        )
    }
}
