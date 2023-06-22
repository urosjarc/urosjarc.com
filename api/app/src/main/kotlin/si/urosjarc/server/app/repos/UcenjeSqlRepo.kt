package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.name
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.repos.*

object UcenjeSqlRepo : UcenjeRepo, SqlRepo<Ucenje>(name<Ucenje>()) {
    val id_ucenec = reference(Ucenje::id_ucenec.name, OsebaSqlRepo.id)
    val id_ucitelj = reference(Ucenje::id_ucitelj.name, OsebaSqlRepo.id)

    override fun map(obj: Ucenje, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[id_ucenec] = obj.id_ucenec.value
        any[id_ucitelj] = obj.id_ucitelj.value
    }

    override fun resultRow(R: ResultRow): Ucenje = Ucenje(
        id = Id(R[id]),
        id_ucenec = Id(R[id_ucenec]),
        id_ucitelj = Id(R[id_ucitelj]),
    )
}
