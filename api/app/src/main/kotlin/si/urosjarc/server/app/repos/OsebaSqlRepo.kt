package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.OsebaRepo

object OsebaSqlRepo : OsebaRepo, SqlRepo<Oseba>(ime<Oseba>()) {
    val ime = varchar(Oseba::ime.name, STR_SHORT)
    val priimek = varchar(Oseba::priimek.name, STR_SHORT)
    val username = varchar(Oseba::username.name, STR_SHORT)
    val tip = varchar(Oseba::tip.name, STR_SHORT)

    override fun zakodiraj(obj: Oseba, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[ime] = obj.ime
        any[priimek] = obj.priimek
        any[username] = obj.username
        any[tip] = obj.tip.name
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Oseba = Oseba(
        id = Id(row(id) as Int),
        ime = row(ime) as String,
        priimek = row(priimek) as String,
        username = row(username) as String,
        tip = Oseba.Tip.valueOf(row(tip) as String),
    )
}
