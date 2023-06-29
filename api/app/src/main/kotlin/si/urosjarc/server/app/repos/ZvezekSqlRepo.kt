package si.urosjarc.server.app.repos

import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.domain.Zvezek
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.ZvezekRepo

object ZvezekSqlRepo : ZvezekRepo, SqlRepo<Zvezek>(ime<Zvezek>()) {
    val tip = varchar(Zvezek::tip.name, STR_SHORT)
    val naslov = varchar(Zvezek::naslov.name, STR_MEDIUM)
}
