package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.name
import si.urosjarc.server.core.domain.Naloga
import si.urosjarc.server.core.domain.Tematika
import si.urosjarc.server.core.domain.Zvezek
import si.urosjarc.server.core.repos.NalogaRepo
import si.urosjarc.server.core.repos.TematikaRepo
import si.urosjarc.server.core.repos.ZvezekRepo

object ZvezekSqlRepo : ZvezekRepo, SqlRepo<Zvezek>(name<Zvezek>()) {
    val tip = varchar(Zvezek::tip.name, STR_SHORT)
    val naslov = varchar(Zvezek::naslov.name, STR_MEDIUM)

    override fun map(obj: Zvezek, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[tip] = obj.tip.name
        any[naslov] = obj.naslov
    }

    override fun resultRow(R: ResultRow): Zvezek = Zvezek(
        id = Id(R[id]),
        tip = Zvezek.Tip.valueOf(R[tip]),
        naslov = R[naslov],
    )
}

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

object NalogaSqlRepo : NalogaRepo, SqlRepo<Naloga>(name<Naloga>()) {
    val resitev = varchar(Naloga::resitev.name, STR_LONG)
    val vsebina = varchar(Naloga::vsebina.name, STR_LONG)
    val tematika_id = reference(Naloga::tematika_id.name, TematikaSqlRepo.id)
    override fun map(obj: Naloga, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[tematika_id] = obj.tematika_id.value
        any[resitev] = obj.resitev
        any[vsebina] = obj.vsebina
    }

    override fun resultRow(R: ResultRow): Naloga = Naloga(
        id = Id(R[id]),
        tematika_id = Id(R[tematika_id]),
        resitev = R[resitev],
        vsebina = R[vsebina]
    )
}
