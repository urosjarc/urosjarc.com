package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Tematika
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.TematikaRepo

object TematikaSqlRepo : TematikaRepo, SqlRepo<Tematika>(ime<Tematika>()) {
    val naslov = varchar(Tematika::naslov.name, STR_MEDIUM)
    val zvezek_id = reference(Tematika::zvezek_id.name, ZvezekSqlRepo.id)
    override fun zakodiraj(obj: Tematika, any: UpdateBuilder<Number>) {
        any[naslov] = obj.naslov
        any[zvezek_id] = obj.zvezek_id.value
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Tematika = Tematika(
        id = Id(row(id) as Int),
        naslov = row(naslov) as String,
        zvezek_id = Id(row(zvezek_id) as Int)
    )
}
