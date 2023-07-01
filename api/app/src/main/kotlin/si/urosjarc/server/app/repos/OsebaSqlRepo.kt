package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.app.extend.izberi
import si.urosjarc.server.core.base.DomenskiGraf
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.OsebaRepo

object OsebaSqlRepo : OsebaRepo, SqlRepo<Oseba>(ime<Oseba>()) {
    val ime = varchar(Oseba::ime.name, STR_SHORT)
    val priimek = varchar(Oseba::priimek.name, STR_SHORT)
    val username = varchar(Oseba::username.name, STR_SHORT)
    val tip = varchar(Oseba::tip.name, STR_SHORT)

    override fun zakodiraj(obj: Oseba, any: UpdateBuilder<Number>) {
        any[ime] = obj.ime
        any[priimek] = obj.priimek
        any[username] = obj.username
        any[tip] = obj.tip.name
    }

    override fun dekodiraj(row: (Column<*>) -> Any?): Oseba = Oseba(
            id = Id(row(id) as Int),
            ime = row(ime) as String,
            priimek = row(priimek) as String,
            username = row(username) as String,
            tip = Oseba.Tip.valueOf(row(tip) as String),
    )

    override fun dobi_kontakte_in_sporocila(id: Id<Oseba>): DomenskiGraf {
        val kontakt_posiljatelja = KontaktSqlRepo.alias("kontakt_posiljatelja")
        val kontakt_prejemnika = KontaktSqlRepo.alias("kontakt_prejemnika")

        return SporociloSqlRepo.join(
                otherTable = kontakt_posiljatelja,
                otherColumn = kontakt_posiljatelja[KontaktSqlRepo.id],
                onColumn = SporociloSqlRepo.posiljatelj_id,
                joinType = JoinType.INNER
        ).join(
                otherTable = kontakt_prejemnika,
                otherColumn = kontakt_prejemnika[KontaktSqlRepo.id],
                onColumn = SporociloSqlRepo.prejemnik_id,
                joinType = JoinType.INNER
        ).izberi(
                kontakt_posiljatelja,
                kontakt_prejemnika,
                SporociloSqlRepo
        ) { kontakt_prejemnika[KontaktSqlRepo.oseba_id].eq(id.value) }
    }

    override fun dobi_tests_in_statuse(id: Id<Oseba>): DomenskiGraf {
        return StatusSqlRepo.join(
                otherTable = TestSqlRepo,
                joinType = JoinType.INNER,
                onColumn = StatusSqlRepo.test_id,
                otherColumn = TestSqlRepo.id
        ).izberi(
                TestSqlRepo,
                NalogaSqlRepo,
                TematikaSqlRepo,
                ZvezekSqlRepo
        ) { TestSqlRepo.oseba_id.eq(id.value) }
    }

    override fun dobi_ucenje(id: Id<Oseba>): DomenskiGraf {
        return UcenjeSqlRepo.izberi(UcenjeSqlRepo) {
            UcenjeSqlRepo.ucenec_id.eq(id.value) or UcenjeSqlRepo.ucitelj_id.eq(id.value)
        }
    }
}
