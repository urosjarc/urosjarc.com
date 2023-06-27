package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Tematika
import si.urosjarc.server.core.extends.name
import si.urosjarc.server.core.repos.TematikaRepo

object TematikaSqlRepo : TematikaRepo, SqlRepo<Tematika>(name<Tematika>()) {
    val naslov = varchar(Tematika::naslov.name, STR_MEDIUM)
    val zvezek_id = reference(Tematika::zvezek_id.name, ZvezekSqlRepo.id)
    override fun map(obj: Tematika, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[naslov] = obj.naslov
        any[zvezek_id] = obj.zvezek_id.value
    }

    override fun resultRow(R: ResultRow): Tematika = Tematika(
        id = Id(R[id]),
        naslov = R[naslov],
        zvezek_id = Id(R[zvezek_id])
    )
}
