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

object ZvezekSqlRepo : ZvezekRepo, SqlRepo<Zvezek>(name<Naloga>()) {
    val tip = varchar(Zvezek::tip.name, STR_SHORT)
    val naslov = varchar(Zvezek::naslov.name, STR_MEDIUM)

    override fun map(obj: Zvezek, any: UpdateBuilder<Number>) {
        any[tip] = obj.tip.name
        any[naslov] = obj.naslov
    }

    override fun resultRow(R: ResultRow): Zvezek = Zvezek(
        tip = Zvezek.Tip.valueOf(R[tip]),
        naslov = R[naslov],
    )
}

object TematikaSqlRepo : TematikaRepo, SqlRepo<Tematika>(name<Naloga>()) {
    val naslov = varchar(Tematika::naslov.name, STR_MEDIUM)
    val id_zvezek = reference(Tematika::id_zvezek.name, ZvezekSqlRepo.id)
    override fun map(obj: Tematika, any: UpdateBuilder<Number>) {
        any[naslov] = obj.naslov
        any[id_zvezek] = obj.id_zvezek.value
    }

    override fun resultRow(R: ResultRow): Tematika = Tematika(
        naslov = R[naslov],
        id_zvezek = Id(R[id_zvezek])
    )
}

object NalogaSqlRepo : NalogaRepo, SqlRepo<Naloga>(name<Naloga>()) {
    val resitev = varchar(Naloga::resitev.name, STR_LONG)
    val vsebina = varchar(Naloga::vsebina.name, STR_LONG)
    val id_tematika = reference(Naloga::id_tematika.name, TematikaSqlRepo.id)
    override fun map(obj: Naloga, any: UpdateBuilder<Number>) {
        any[id_tematika] = obj.id_tematika.value
        any[resitev] = obj.resitev
        any[vsebina] = obj.vsebina
    }

    override fun resultRow(R: ResultRow): Naloga = Naloga(
        id_tematika = Id(R[id_tematika]),
        resitev = R[resitev],
        vsebina = R[vsebina]
    )
}
