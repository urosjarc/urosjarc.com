package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.app.extend.izberi
import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Status
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.StatusRepo

object StatusSqlRepo : StatusRepo, SqlRepo<Status>(ime<Status>()) {
    val tip = varchar(Status::tip.name, STR_SHORT)
    val naloga_id = reference(Status::naloga_id.name, NalogaSqlRepo.id)
    val test_id = reference(Status::test_id.name, TestSqlRepo.id)
    val pojasnilo = varchar(Status::pojasnilo.name, STR_LONG)

    override fun zakodiraj(obj: Status, any: UpdateBuilder<Number>) {
        any[tip] = obj.tip.name
        any[naloga_id] = obj.naloga_id.value
        any[test_id] = obj.test_id.value
        any[pojasnilo] = obj.pojasnilo
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Status = Status(
        id = Id(row(id) as Int),
        tip = Status.Tip.valueOf(row(tip) as String),
        naloga_id = Id(row(naloga_id) as Int),
        test_id = Id(row(test_id) as Int),
        pojasnilo = row(pojasnilo) as String
    )

}
