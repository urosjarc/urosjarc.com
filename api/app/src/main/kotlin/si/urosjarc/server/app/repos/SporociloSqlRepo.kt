package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.app.extend.izberi
import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Sporocilo
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.SporociloRepo

object SporociloSqlRepo : SporociloRepo, SqlRepo<Sporocilo>(ime<Sporocilo>()) {
    val posiljatelj_id = reference(Sporocilo::posiljatelj_id.name, KontaktSqlRepo.id)
    val prejemnik_id = reference(Sporocilo::prejemnik_id.name, KontaktSqlRepo.id)
    val vsebina = varchar(Sporocilo::vsebina.name, STR_LONG)

    override fun zakodiraj(obj: Sporocilo, any: UpdateBuilder<Number>) {
        any[posiljatelj_id] = obj.posiljatelj_id.value
        any[prejemnik_id] = obj.prejemnik_id.value
        any[vsebina] = obj.vsebina
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Sporocilo = Sporocilo(
        id = Id(row(id) as Int),
        posiljatelj_id = Id(row(posiljatelj_id) as Int),
        prejemnik_id = Id(row(prejemnik_id) as Int),
        vsebina = row(vsebina) as String,
    )
}
