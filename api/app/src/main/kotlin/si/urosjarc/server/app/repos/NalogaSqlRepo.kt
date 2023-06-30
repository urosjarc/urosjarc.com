package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Naloga
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.NalogaRepo

object NalogaSqlRepo : NalogaRepo, SqlRepo<Naloga>(ime<Naloga>()) {
    val resitev = varchar(Naloga::resitev.name, STR_LONG)
    val vsebina = varchar(Naloga::vsebina.name, STR_LONG)
    val tematika_id = reference(Naloga::tematika_id.name, TematikaSqlRepo.id)

    override fun zakodiraj(obj: Naloga, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[tematika_id] = obj.tematika_id.value
        any[resitev] = obj.resitev
        any[vsebina] = obj.vsebina
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Naloga = Naloga(
        id = Id(row(id) as Int),
        tematika_id = Id(row(tematika_id) as Int),
        resitev = row(resitev) as String,
        vsebina = row(vsebina) as String
    )
}
