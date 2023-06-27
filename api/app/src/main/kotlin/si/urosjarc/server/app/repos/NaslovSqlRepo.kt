package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Naslov
import si.urosjarc.server.core.extends.name
import si.urosjarc.server.core.repos.NaslovRepo

object NaslovSqlRepo : NaslovRepo, SqlRepo<Naslov>(name<Naslov>()) {
    val oseba_id = reference(Naslov::oseba_id.name, OsebaSqlRepo.id)
    val drzava = varchar(Naslov::drzava.name, STR_SHORT)
    val mesto = varchar(Naslov::mesto.name, STR_SHORT)
    val ulica = varchar(Naslov::ulica.name, STR_SHORT)
    val zip = integer(Naslov::zip.name)
    val dodatno = varchar(Naslov::dodatno.name, STR_SHORT)

    override fun map(obj: Naslov, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[oseba_id] = obj.oseba_id.value
        any[drzava] = obj.drzava
        any[mesto] = obj.mesto
        any[ulica] = obj.ulica
        any[zip] = obj.zip
        any[dodatno] = obj.dodatno
    }

    override fun resultRow(R: ResultRow): Naslov = Naslov(
        oseba_id = Id(R[oseba_id]),
        drzava = R[drzava],
        mesto = R[mesto],
        ulica = R[ulica],
        zip = R[zip],
        dodatno = R[dodatno],
    )
}
