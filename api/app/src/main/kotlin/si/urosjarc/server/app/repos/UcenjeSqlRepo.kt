package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.app.extend.izberi
import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Ucenje
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.UcenjeRepo


object UcenjeSqlRepo : UcenjeRepo, SqlRepo<Ucenje>(ime<Ucenje>()) {
    val ucenec_id = reference(Ucenje::ucenec_id.name, OsebaSqlRepo.id)
    val ucitelj_id = reference(Ucenje::ucitelj_id.name, OsebaSqlRepo.id)

    override fun zakodiraj(obj: Ucenje, any: UpdateBuilder<Number>) {
        any[ucenec_id] = obj.ucenec_id.value
        any[ucitelj_id] = obj.ucitelj_id.value
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Ucenje = Ucenje(
        id = Id(row(id) as Int),
        ucenec_id = Id(row(ucenec_id) as Int),
        ucitelj_id = Id(row(ucitelj_id) as Int),
    )

    override fun dobi_ucence(id_ucitelja: Id<Oseba>): DomenskiGraf {
        return join(
            OsebaSqlRepo,
            onColumn = ucenec_id, otherColumn = OsebaSqlRepo.id,
            joinType = JoinType.INNER
        ).izberi(OsebaSqlRepo) { ucitelj_id.eq(id_ucitelja.value) }
    }

    override fun dobi_ucitelje(id_ucenca: Id<Oseba>): DomenskiGraf {
        return join(
            OsebaSqlRepo,
            onColumn = ucitelj_id, otherColumn = OsebaSqlRepo.id,
            joinType = JoinType.INNER
        ).izberi(OsebaSqlRepo) { ucenec_id.eq(id_ucenca.value) }
    }

}
