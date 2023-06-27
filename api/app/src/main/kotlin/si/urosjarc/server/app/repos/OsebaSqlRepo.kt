package si.urosjarc.server.app.repos

import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.app.extend.sliceAlias
import si.urosjarc.server.app.extend.toAdjecentJsonElement
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.name
import si.urosjarc.server.core.domain.Kontakt
import si.urosjarc.server.core.domain.Naslov
import si.urosjarc.server.core.domain.Oseba
import si.urosjarc.server.core.domain.Sporocilo
import si.urosjarc.server.core.repos.KontaktRepo
import si.urosjarc.server.core.repos.NaslovRepo
import si.urosjarc.server.core.repos.OsebaRepo
import si.urosjarc.server.core.repos.SporociloRepo

object OsebaSqlRepo : OsebaRepo, SqlRepo<Oseba>(name<Oseba>()) {
    val ime = varchar(Oseba::ime.name, STR_SHORT)
    val priimek = varchar(Oseba::priimek.name, STR_SHORT)
    val username = varchar(Oseba::username.name, STR_SHORT)
    val tip = varchar(Oseba::tip.name, STR_SHORT)

    override fun map(obj: Oseba, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[ime] = obj.ime
        any[priimek] = obj.priimek
        any[username] = obj.username
        any[tip] = obj.tip.name
    }

    override fun resultRow(R: ResultRow): Oseba = Oseba(
        id = Id(R[id]),
        ime = R[ime],
        priimek = R[priimek],
        username = R[username],
        tip = Oseba.Tip.valueOf(R[tip]),
    )
}

object NaslovSqlRepo : NaslovRepo, SqlRepo<Naslov>(name<Naslov>()) {
    val oseba_id = reference(Naslov::oseba_id.name, OsebaSqlRepo.id)
    val drzava = varchar(Naslov::drzava.name, STR_SHORT)
    val mesto = varchar(Naslov::mesto.name, STR_SHORT)
    val ulica = varchar(Naslov::ulica.name, STR_SHORT)
    val zip = integer(Naslov::zip.name)
    val dodatno = varchar(Naslov::dodatno.name, STR_SHORT)

    override fun map(obj: Naslov, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[oseba_id] = obj.oseba_id.value
        any[drzava] = obj.drzava
        any[mesto] = obj.mesto
        any[ulica] = obj.ulica
        any[zip] = obj.zip
        any[dodatno] = obj.dodatno
    }

    override fun resultRow(R: ResultRow): Naslov = Naslov(
        oseba_id = Id(R[oseba_id]),
        drzava = R[drzava],
        mesto = R[mesto],
        ulica = R[ulica],
        zip = R[zip],
        dodatno = R[dodatno],
    )
}

object KontaktSqlRepo : KontaktRepo, SqlRepo<Kontakt>(name<Kontakt>()) {
    val oseba_id = reference(Kontakt::oseba_id.name, OsebaSqlRepo.id)
    val data = varchar(Kontakt::data.name, STR_SHORT)
    val tip = varchar(Kontakt::tip.name, STR_SHORT)

    override fun map(obj: Kontakt, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[oseba_id] = obj.oseba_id.value
        any[data] = obj.data
        any[tip] = obj.tip.name
    }

    override fun resultRow(R: ResultRow): Kontakt = Kontakt(
        id = Id(R[id]),
        oseba_id = Id(R[oseba_id]),
        data = R[data],
        tip = Kontakt.Tip.valueOf(R[tip])
    )
}

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

    override fun get_posiljatelje(id_prejemnika: Id<Oseba>): JsonElement {
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
        ).toAdjecentJsonElement()
    }
}
