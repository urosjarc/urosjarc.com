package si.urosjarc.server.app.repos

import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.extend.toJsonElement
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.name
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Ucenje
import si.urosjarc.server.core.repos.UcenjeRepo


object UcenjeSqlRepo : UcenjeRepo, SqlRepo<Ucenje>(name<Ucenje>()) {
    val id_ucenec = reference(Ucenje::ucenec_id.name, OsebaSqlRepo.id)
    val id_ucitelj = reference(Ucenje::ucitelj_id.name, OsebaSqlRepo.id)

    override fun map(obj: Ucenje, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[id_ucenec] = obj.ucenec_id.value
        any[id_ucitelj] = obj.ucitelj_id.value
    }

    override fun resultRow(R: ResultRow): Ucenje = Ucenje(
        id = Id(R[id]),
        ucenec_id = Id(R[id_ucenec]),
        ucitelj_id = Id(R[id_ucitelj]),
    )

    override fun get_ucence(id_ucitelja: Id<Oseba>): JsonElement {
        return join(
            OsebaSqlRepo,
            onColumn = id_ucenec, otherColumn = OsebaSqlRepo.id,
            joinType = JoinType.INNER
        ).slice(OsebaSqlRepo.fields).select { UcenjeSqlRepo.id_ucitelj.eq(id_ucitelja.value) }.toJsonElement()
    }

    override fun get_ucitelje(id_ucenca: Id<Oseba>): JsonElement {
        return join(
            OsebaSqlRepo,
            onColumn = id_ucitelj, otherColumn = OsebaSqlRepo.id,
            joinType = JoinType.INNER
        ).slice(OsebaSqlRepo.fields).select { UcenjeSqlRepo.id_ucenec.eq(id_ucenca.value) }.toJsonElement()
    }

}
