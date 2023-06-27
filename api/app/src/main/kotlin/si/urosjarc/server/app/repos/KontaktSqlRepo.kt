package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.extends.name
import si.urosjarc.server.core.domain.Kontakt
import si.urosjarc.server.core.repos.KontaktRepo

object KontaktSqlRepo : KontaktRepo, SqlRepo<Kontakt>(name<Kontakt>()) {
    val oseba_id = reference(Kontakt::oseba_id.name, OsebaSqlRepo.id)
    val data = varchar(Kontakt::data.name, STR_SHORT)
    val tip = varchar(Kontakt::tip.name, STR_SHORT)

    override fun map(obj: Kontakt, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[oseba_id] = obj.oseba_id.value
        any[data] = obj.data
        any[tip] = obj.tip.name
    }

    override fun resultRow(R: ResultRow): Kontakt = Kontakt(
        id = Id(R[id]),
        oseba_id = Id(R[oseba_id]),
        data = R[data],
        tip = Kontakt.Tip.valueOf(R[tip])
    )
}
