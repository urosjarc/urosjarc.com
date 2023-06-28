package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.base.SqlRepo
import si.urosjarc.server.app.extend.sliceAlias
import si.urosjarc.server.app.extend.toDomainMap
import si.urosjarc.server.core.base.DomainMap
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Sporocilo
import si.urosjarc.server.core.extends.name
import si.urosjarc.server.core.repos.SporociloRepo

object SporociloSqlRepo : SporociloRepo, SqlRepo<Sporocilo>(name<Sporocilo>()) {
    val posiljatelj_id = reference(Sporocilo::posiljatelj_id.name, KontaktSqlRepo.id)
    val prejemnik_id = reference(Sporocilo::prejemnik_id.name, KontaktSqlRepo.id)
    val vsebina = varchar(Sporocilo::vsebina.name, STR_LONG)

    override fun map(obj: Sporocilo, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[posiljatelj_id] = obj.posiljatelj_id.value
        any[prejemnik_id] = obj.prejemnik_id.value
        any[vsebina] = obj.vsebina
    }

    override fun resultRow(R: ResultRow): Sporocilo = Sporocilo(
        id = Id(R[id]),
        posiljatelj_id = Id(R[posiljatelj_id]),
        prejemnik_id = Id(R[prejemnik_id]),
        vsebina = R[vsebina],
    )

    override fun get_posiljatelje(id_prejemnika: Id<Oseba>): DomainMap {
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
        ).sliceAlias(
            kontakt_posiljatelja,
            kontakt_prejemnika,
            oseba_posiljatelj
        ).select(
            kontakt_prejemnika[KontaktSqlRepo.oseba_id].eq(id_prejemnika.value)
        ).toDomainMap()
    }
}
