package si.urosjarc.server.app.repos

import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.app.extend.sliceAlias
import si.urosjarc.server.app.extend.toAdjecentJsonElement
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Ucenje
import si.urosjarc.server.core.extends.name
import si.urosjarc.server.core.repos.UcenjeRepo


object UcenjeSqlRepo : UcenjeRepo, SqlRepo<Ucenje>(name<Ucenje>()) {
    val ucenec_id = reference(Ucenje::ucenec_id.name, OsebaSqlRepo.id)
    val ucitelj_id = reference(Ucenje::ucitelj_id.name, OsebaSqlRepo.id)

    override fun map(obj: Ucenje, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[ucenec_id] = obj.ucenec_id.value
        any[ucitelj_id] = obj.ucitelj_id.value
    }

    override fun resultRow(R: ResultRow): Ucenje = Ucenje(
        id = Id(R[id]),
        ucenec_id = Id(R[ucenec_id]),
        ucitelj_id = Id(R[ucitelj_id]),
    )

    override fun get_ucence(id_ucitelja: Id<Oseba>): JsonElement {
        return join(
            OsebaSqlRepo,
            onColumn = ucenec_id, otherColumn = OsebaSqlRepo.id,
            joinType = JoinType.INNER
        )
            .sliceAlias(OsebaSqlRepo)
            .select { ucitelj_id.eq(id_ucitelja.value) }
            .toAdjecentJsonElement()
    }

    override fun get_ucitelje(id_ucenca: Id<Oseba>): JsonElement {
        return join(
            OsebaSqlRepo,
            onColumn = ucitelj_id, otherColumn = OsebaSqlRepo.id,
            joinType = JoinType.INNER
        )
            .sliceAlias(OsebaSqlRepo)
            .select { ucenec_id.eq(id_ucenca.value) }
            .toAdjecentJsonElement()
    }

}
