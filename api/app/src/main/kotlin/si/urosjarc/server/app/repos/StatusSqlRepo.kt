package si.urosjarc.server.app.repos

import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.app.extend.sliceAlias
import si.urosjarc.server.app.extend.toAdjecentJsonElement
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Status
import si.urosjarc.server.core.extends.name
import si.urosjarc.server.core.repos.StatusRepo

object StatusSqlRepo : StatusRepo, SqlRepo<Status>(name<Status>()) {
    val tip = varchar(Status::tip.name, STR_SHORT)
    val naloga_id = reference(Status::naloga_id.name, NalogaSqlRepo.id)
    val test_id = reference(Status::test_id.name, TestSqlRepo.id)
    val pojasnilo = varchar(Status::pojasnilo.name, STR_LONG)

    override fun map(obj: Status, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[tip] = obj.tip.name
        any[naloga_id] = obj.naloga_id.value
        any[test_id] = obj.test_id.value
        any[pojasnilo] = obj.pojasnilo
    }

    override fun resultRow(R: ResultRow): Status = Status(
        id = Id(R[id]),
        tip = Status.Tip.valueOf(R[tip]),
        naloga_id = Id(R[naloga_id]),
        test_id = Id(R[test_id]),
        pojasnilo = R[pojasnilo]
    )

    override fun get_statuse(id_osebe: Id<Oseba>): JsonElement {
        return join(
            otherTable = TestSqlRepo,
            joinType = JoinType.INNER,
            onColumn = test_id,
            otherColumn = TestSqlRepo.id
        ).join(
            otherTable = NalogaSqlRepo,
            joinType = JoinType.INNER,
            onColumn = naloga_id,
            otherColumn = NalogaSqlRepo.id
        ).join(
            otherTable = TematikaSqlRepo,
            joinType = JoinType.INNER,
            onColumn = NalogaSqlRepo.tematika_id,
            otherColumn = TematikaSqlRepo.id
        ).join(
            otherTable = ZvezekSqlRepo,
            joinType = JoinType.INNER,
            onColumn = TematikaSqlRepo.zvezek_id,
            otherColumn = ZvezekSqlRepo.id
        ).sliceAlias(
            this,
            TestSqlRepo,
            NalogaSqlRepo,
            TematikaSqlRepo,
            ZvezekSqlRepo
        )
            .select(where = { TestSqlRepo.oseba_id.eq(id_osebe.value) })
            .toAdjecentJsonElement(children = true)
    }

}
