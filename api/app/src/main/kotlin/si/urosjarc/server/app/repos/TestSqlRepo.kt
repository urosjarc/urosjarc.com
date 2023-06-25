package si.urosjarc.server.app.repos

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.extend.sliceAlias
import si.urosjarc.server.app.extend.toJsonElement
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.name
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Status
import si.urosjarc.server.core.domain.Test
import si.urosjarc.server.core.repos.StatusRepo
import si.urosjarc.server.core.repos.TestRepo

object StatusSqlRepo : StatusRepo, SqlRepo<Status>(name<Status>()) {
    val tip = varchar(Status::tip.name, STR_SHORT)
    val id_naloga = reference(Status::naloga_id.name, NalogaSqlRepo.id)
    val id_test = reference(Status::test_id.name, TestSqlRepo.id)
    val pojasnilo = varchar(Status::pojasnilo.name, STR_LONG)

    override fun map(obj: Status, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[tip] = obj.tip.name
        any[id_naloga] = obj.naloga_id.value
        any[id_test] = obj.test_id.value
        any[pojasnilo] = obj.pojasnilo
    }

    override fun resultRow(R: ResultRow): Status = Status(
        id = Id(R[id]),
        tip = Status.Tip.valueOf(R[tip]),
        naloga_id = Id(R[id_naloga]),
        test_id = Id(R[id_test]),
        pojasnilo = R[pojasnilo]
    )

    override fun get_statuse(id_osebe: Id<Oseba>): JsonElement {
        return join(
            otherTable = TestSqlRepo,
            joinType = JoinType.INNER,
            onColumn = id_test,
            otherColumn = TestSqlRepo.id
        ).join(
            otherTable = NalogaSqlRepo,
            joinType = JoinType.INNER,
            onColumn = id_naloga,
            otherColumn = NalogaSqlRepo.id
        ).join(
            otherTable = TematikaSqlRepo,
            joinType = JoinType.INNER,
            onColumn = NalogaSqlRepo.id_tematika,
            otherColumn = TematikaSqlRepo.id
        ).join(
            otherTable = ZvezekSqlRepo,
            joinType = JoinType.INNER,
            onColumn = TematikaSqlRepo.id_zvezek,
            otherColumn = ZvezekSqlRepo.id
        ).sliceAlias(
            this,
            TestSqlRepo,
            NalogaSqlRepo,
            TematikaSqlRepo,
            ZvezekSqlRepo
        ).select(where = { TestSqlRepo.id_oseba.eq(id_osebe.value) }).toJsonElement()
    }
}

object TestSqlRepo : TestRepo, SqlRepo<Test>(name<Test>()) {
    val naslov = varchar(Test::naslov.name, STR_MEDIUM)
    val podnaslov = varchar(Test::podnaslov.name, STR_MEDIUM)
    val deadline = date(Test::deadline.name)
    val id_oseba = reference(Test::oseba_id.name, OsebaSqlRepo.id)
    override fun map(obj: Test, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[naslov] = obj.naslov
        any[podnaslov] = obj.podnaslov
        any[deadline] = obj.deadline.toJavaLocalDate()
        any[id_oseba] = obj.oseba_id.value
    }

    override fun resultRow(R: ResultRow): Test {
        val deadline = R[deadline]
        return Test(
            id = Id(R[id]),
            naslov = R[naslov],
            podnaslov = R[podnaslov],
            oseba_id = Id(R[id_oseba]),
            deadline = deadline.toKotlinLocalDate()
        )
    }
}
