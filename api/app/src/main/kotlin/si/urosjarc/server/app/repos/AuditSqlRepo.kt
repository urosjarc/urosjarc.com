package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Audit
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.AuditRepo

object AuditSqlRepo : AuditRepo, SqlRepo<Audit>(ime<Audit>()) {
    val opis = varchar(Audit::opis.name, STR_LONG)
    val entiteta = varchar(Audit::entiteta.name, STR_SHORT)
    val entiteta_id = integer(Audit::entiteta_id.name)

    override fun zakodiraj(obj: Audit, any: UpdateBuilder<Number>) {
        any[opis] = obj.opis
        any[entiteta] = obj.entiteta
        any[entiteta_id] = obj.entiteta_id
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Audit = Audit(
        id = Id(row(id) as Int),
        opis = row(opis) as String,
        entiteta = row(entiteta) as String,
        entiteta_id = row(entiteta_id) as Int,
    )
}
