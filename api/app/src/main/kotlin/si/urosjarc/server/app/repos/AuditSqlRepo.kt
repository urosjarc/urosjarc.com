package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Audit
import si.urosjarc.server.core.extends.name
import si.urosjarc.server.core.repos.AuditRepo

object AuditSqlRepo : AuditRepo, SqlRepo<Audit>(name<Audit>()) {
    val opis = varchar(Audit::opis.name, STR_LONG)
    val entiteta = varchar(Audit::entiteta.name, STR_SHORT)
    val entiteta_id = varchar(Audit::entiteta_id.name, STR_SHORT)

    override fun map(obj: Audit, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[opis] = obj.opis
        any[entiteta] = obj.entiteta
        any[entiteta_id] = obj.entiteta_id
    }

    override fun resultRow(R: ResultRow): Audit = Audit(
        id=Id(R[id]),
        opis = R[opis],
        entiteta = R[entiteta],
        entiteta_id = R[entiteta_id],
    )
}
