package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
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

    override fun zakodiraj(obj: Naslov, any: UpdateBuilder<Number>) {
        any[oseba_id] = obj.oseba_id.value
        any[drzava] = obj.drzava
        any[mesto] = obj.mesto
        any[ulica] = obj.ulica
        any[zip] = obj.zip
        any[dodatno] = obj.dodatno
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Naslov = Naslov(
        id = Id(row(id) as Int),
        oseba_id = Id(row(oseba_id) as Int),
        drzava = row(drzava) as String,
        mesto = row(mesto) as String,
        ulica = row(ulica) as String,
        zip = row(zip) as Int,
        dodatno = row(dodatno) as String,
    )
}
