package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Naslov
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.NaslovRepo

object NaslovSqlRepo : NaslovRepo, SqlRepo<Naslov>(ime<Naslov>()) {
    val oseba_id = reference(Naslov::oseba_id.name, OsebaSqlRepo.id)
    val drzava = varchar(Naslov::drzava.name, STR_SHORT)
    val mesto = varchar(Naslov::mesto.name, STR_SHORT)
    val ulica = varchar(Naslov::ulica.name, STR_SHORT)
    val zip = integer(Naslov::zip.name)
    val dodatno = varchar(Naslov::dodatno.name, STR_SHORT)
}
