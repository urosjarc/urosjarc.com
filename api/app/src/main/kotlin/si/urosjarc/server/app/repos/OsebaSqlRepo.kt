package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
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

    override fun dekodiraj(R: ResultRow): Oseba = Oseba(
        id = Id(R[id]),
        ime = R[ime],
        priimek = R[priimek],
        username = R[username],
        tip = Oseba.Tip.valueOf(R[tip]),
    )
}
