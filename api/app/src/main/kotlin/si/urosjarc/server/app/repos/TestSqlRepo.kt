package si.urosjarc.server.app.repos

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Test
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.TestRepo
import java.time.LocalDate

object TestSqlRepo : TestRepo, SqlRepo<Test>(ime<Test>()) {
    val naslov = varchar(Test::naslov.name, STR_MEDIUM)
    val podnaslov = varchar(Test::podnaslov.name, STR_MEDIUM)
    val deadline = date(Test::deadline.name)
    val oseba_id = reference(Test::oseba_id.name, OsebaSqlRepo.id)
    override fun zakodiraj(obj: Test, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[naslov] = obj.naslov
        any[podnaslov] = obj.podnaslov
        any[deadline] = obj.deadline.toJavaLocalDate()
        any[oseba_id] = obj.oseba_id.value
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Test = Test(
        id = Id(row(id) as Int),
        naslov = row(naslov) as String,
        podnaslov = row(podnaslov) as String,
        oseba_id = Id(row(oseba_id) as Int),
        deadline = (row(deadline) as LocalDate).toKotlinLocalDate()
    )
}
