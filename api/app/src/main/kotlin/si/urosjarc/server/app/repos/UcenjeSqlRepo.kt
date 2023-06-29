package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.app.extend.vzemi
import si.urosjarc.server.app.extend.vDomenskiGraf
import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.UcenjeRepo


object UcenjeSqlRepo : UcenjeRepo, SqlRepo<Ucenje>(ime<Ucenje>()) {
    val ucenec_id = reference(Ucenje::ucenec_id.name, OsebaSqlRepo.id)
    val ucitelj_id = reference(Ucenje::ucitelj_id.name, OsebaSqlRepo.id)

    override fun zakodiraj(obj: Ucenje, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[ucenec_id] = obj.ucenec_id.value
        any[ucitelj_id] = obj.ucitelj_id.value
    }

    override fun dekodiraj(R: ResultRow): Ucenje = Ucenje(
        id = Id(R[id]),
        ucenec_id = Id(R[ucenec_id]),
        ucitelj_id = Id(R[ucitelj_id]),
    )

    override fun dobi_ucence(id_ucitelja: Id<Oseba>): DomenskiGraf {
        return join(
            OsebaSqlRepo,
            onColumn = ucenec_id, otherColumn = OsebaSqlRepo.id,
            joinType = JoinType.INNER
        )
            .vzemi(OsebaSqlRepo)
            .select { ucitelj_id.eq(id_ucitelja.value) }
            .vDomenskiGraf()
    }

    override fun dobi_ucitelje(id_ucenca: Id<Oseba>): DomenskiGraf {
        return join(
            OsebaSqlRepo,
            onColumn = ucitelj_id, otherColumn = OsebaSqlRepo.id,
            joinType = JoinType.INNER
        )
            .vzemi(OsebaSqlRepo)
            .select { ucenec_id.eq(id_ucenca.value) }
            .vDomenskiGraf()

    }

}
