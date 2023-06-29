package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.select
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.app.extend.vDomenskiGraf
import si.urosjarc.server.app.extend.vzemi
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Sporocilo
import si.urosjarc.server.core.extend.ime
import si.urosjarc.server.core.repos.SporociloRepo

object SporociloSqlRepo : SporociloRepo, SqlRepo<Sporocilo>(ime<Sporocilo>()) {
    val posiljatelj_id = reference(Sporocilo::posiljatelj_id.name, KontaktSqlRepo.id)
    val prejemnik_id = reference(Sporocilo::prejemnik_id.name, KontaktSqlRepo.id)
    val vsebina = varchar(Sporocilo::vsebina.name, STR_LONG)
    override fun dobi_posiljatelje(id_prejemnika: Id<Oseba>): SporociloRepo.DobiPosiljateljeGraf {
        val kontakt_posiljatelja = KontaktSqlRepo.alias("kontakt_posiljatelja")
        val kontakt_prejemnika = KontaktSqlRepo.alias("kontakt_prejemnika")
        val oseba_posiljatelj = OsebaSqlRepo.alias("oseba_posiljatelj")

        return join(
            otherTable = kontakt_posiljatelja,
            otherColumn = kontakt_posiljatelja[KontaktSqlRepo.id],
            onColumn = posiljatelj_id,
            joinType = JoinType.INNER
        ).join(
            otherTable = kontakt_prejemnika,
            otherColumn = kontakt_prejemnika[KontaktSqlRepo.id],
            onColumn = prejemnik_id,
            joinType = JoinType.INNER
        ).join(
            otherTable = oseba_posiljatelj,
            otherColumn = oseba_posiljatelj[OsebaSqlRepo.id],
            onColumn = kontakt_posiljatelja[KontaktSqlRepo.oseba_id],
            joinType = JoinType.INNER
        ).vzemi(
            kontakt_posiljatelja,
            kontakt_prejemnika,
            oseba_posiljatelj
        ).select(
            kontakt_prejemnika[KontaktSqlRepo.oseba_id].eq(id_prejemnika.value)
        ).vDomenskiGraf()
    }
}
