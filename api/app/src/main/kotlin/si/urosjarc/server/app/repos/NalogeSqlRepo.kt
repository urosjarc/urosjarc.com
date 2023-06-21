package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.name
import si.urosjarc.server.core.domain.Naloga
import si.urosjarc.server.core.repos.NalogeRepo

class NalogeSqlRepo : NalogeRepo, SqlRepo<Naloga>(name<Naloga>()) {
    val ime: Column<String> = varchar(Naloga::ime.name, 50)

    override fun map(obj: Naloga, any: UpdateBuilder<Number>) {
        any[ime] = obj.ime
    }

    override fun resultRow(R: ResultRow): Naloga = Naloga(
        id = Id(R[id]),
        ime = R[ime]
    )

}
