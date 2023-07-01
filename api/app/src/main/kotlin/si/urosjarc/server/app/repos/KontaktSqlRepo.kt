package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Kontakt
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.KontaktRepo

object KontaktSqlRepo : KontaktRepo, SqlRepo<Kontakt>(ime<Kontakt>()) {
    val oseba_id = reference(Kontakt::oseba_id.name, OsebaSqlRepo.id)
    val data = varchar(Kontakt::data.name, STR_SHORT)
    val tip = varchar(Kontakt::tip.name, STR_SHORT)

    override fun zakodiraj(obj: Kontakt, any: UpdateBuilder<Number>) {
        any[oseba_id] = obj.oseba_id.value
        any[data] = obj.data
        any[tip] = obj.tip.name
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Kontakt = Kontakt(
        id = Id(row(id) as Int),
        oseba_id = Id(row(oseba_id) as Int),
        data = row(data) as String,
        tip = Kontakt.Tip.valueOf(row(tip) as String)
    )
}
