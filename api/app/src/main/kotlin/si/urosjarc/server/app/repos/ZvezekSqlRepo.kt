package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Zvezek
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.ZvezekRepo

object ZvezekSqlRepo : ZvezekRepo, SqlRepo<Zvezek>(ime<Zvezek>()) {
    val tip = varchar(Zvezek::tip.name, STR_SHORT)
    val naslov = varchar(Zvezek::naslov.name, STR_MEDIUM)

    override fun zakodiraj(obj: Zvezek, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[tip] = obj.tip.name
        any[naslov] = obj.naslov
    }

    override fun dekodiraj(R: ResultRow): Zvezek = Zvezek(
        id = Id(R[id]),
        tip = Zvezek.Tip.valueOf(R[tip]),
        naslov = R[naslov],
    )
}
