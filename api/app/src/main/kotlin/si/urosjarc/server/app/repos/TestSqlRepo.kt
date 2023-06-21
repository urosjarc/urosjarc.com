package si.urosjarc.server.app.repos

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.dateParam
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.name
import si.urosjarc.server.core.domain.Status
import si.urosjarc.server.core.domain.Test
import si.urosjarc.server.core.repos.StatusRepo
import si.urosjarc.server.core.repos.TestRepo

object StatusSqlRepo : StatusRepo, SqlRepo<Status>(name<Status>()) {
    val tip = varchar(Status::tip.name, STR_SHORT)
    val id_naloga = reference(Status::id_naloga.name, NalogaSqlRepo.id)
    val id_test = reference(Status::id_test.name, TestSqlRepo.id)

    override fun map(obj: Status, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[tip] = obj.tip.name
        any[id_naloga] = obj.tip.name
        any[id_test] = obj.id_test.value
    }

    override fun resultRow(R: ResultRow): Status = Status(
        id = Id(R[id]),
        tip = Status.Tip.valueOf(R[tip]),
        id_naloga = Id(R[id_naloga]),
        id_test = Id(R[id_test]),
    )
}

object TestSqlRepo : TestRepo, SqlRepo<Test>(name<Test>()) {
    val naslov = varchar(Test::naslov.name, STR_MEDIUM)
    val podnaslov = varchar(Test::podnaslov.name, STR_MEDIUM)
    val deadline = date(Test::deadline.name)
    val id_oseba = reference(Test::id_oseba.name, OsebaSqlRepo.id)
    override fun map(obj: Test, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[naslov] = obj.naslov
        any[podnaslov] = obj.podnaslov
        any[deadline] = obj.deadline.toJavaLocalDate()
        any[id_oseba] = obj.id_oseba.value
    }

    override fun resultRow(R: ResultRow): Test {
        val deadline = R[deadline]
        return Test(
            id = Id(R[id]),
            naslov = R[naslov],
            podnaslov = R[podnaslov],
            id_oseba = Id(R[id_oseba]),
            deadline = deadline.toKotlinLocalDate()
        )
    }
}
