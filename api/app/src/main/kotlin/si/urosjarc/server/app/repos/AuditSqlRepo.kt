package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.name
import si.urosjarc.server.core.domain.Audit
import si.urosjarc.server.core.repos.AuditRepo

object AuditSqlRepo : AuditRepo, SqlRepo<Audit>(name<Audit>()) {
    val opis = varchar(Audit::opis.name, STR_LONG)
    val entiteta = varchar(Audit::entiteta.name, STR_SHORT)
    val id_entiteta = varchar(Audit::id_entiteta.name, STR_SHORT)

    override fun map(obj: Audit, any: UpdateBuilder<Number>) {
        any[opis] = obj.opis
        any[entiteta] = obj.entiteta
        any[id_entiteta] = obj.id_entiteta.value
    }

    override fun resultRow(R: ResultRow): Audit = Audit(
        opis = R[opis],
        entiteta = R[entiteta],
        id_entiteta = Id(R[id_entiteta]),
    )
}
