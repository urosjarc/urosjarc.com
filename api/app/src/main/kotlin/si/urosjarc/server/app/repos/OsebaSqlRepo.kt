package si.urosjarc.server.app.repos

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.base.name
import si.urosjarc.server.core.domain.*
import si.urosjarc.server.core.repos.*

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
        ime = R[ime],
        priimek = R[priimek],
        username = R[username],
        tip = Oseba.Tip.valueOf(R[tip]),
    )
}

object NaslovSqlRepo : NaslovRepo, SqlRepo<Naslov>(name<Naslov>()) {
    val id_oseba = reference(Naslov::id_oseba.name, OsebaSqlRepo.id)
    val drzava = varchar(Naslov::drzava.name, STR_SHORT)
    val mesto = varchar(Naslov::mesto.name, STR_SHORT)
    val ulica = varchar(Naslov::ulica.name, STR_SHORT)
    val zip = integer(Naslov::zip.name)
    val dodatno = varchar(Naslov::dodatno.name, STR_SHORT)

    override fun map(obj: Naslov, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[id_oseba] = obj.id_oseba.value
        any[drzava] = obj.drzava
        any[mesto] = obj.mesto
        any[ulica] = obj.ulica
        any[zip] = obj.zip
        any[dodatno] = obj.dodatno
    }

    override fun resultRow(R: ResultRow): Naslov = Naslov(
        id_oseba = Id(R[id_oseba]),
        drzava = R[drzava],
        mesto = R[mesto],
        ulica = R[ulica],
        zip = R[zip],
        dodatno = R[dodatno],
    )
}

object KontaktSqlRepo : KontaktRepo, SqlRepo<Kontakt>(name<Kontakt>()) {
    val id_oseba = reference(Kontakt::id_oseba.name, OsebaSqlRepo.id)
    val data = varchar(Kontakt::data.name, STR_SHORT)
    val tip = varchar(Kontakt::tip.name, STR_SHORT)

    override fun map(obj: Kontakt, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[id_oseba] = obj.id_oseba.value
        any[data] = obj.data
        any[tip] = obj.tip.name
    }

    override fun resultRow(R: ResultRow): Kontakt = Kontakt(
        id = Id(R[id]),
        id_oseba = Id(R[id_oseba]),
        data = R[data],
        tip = Kontakt.Tip.valueOf(R[tip])
    )
}

object SporociloSqlRepo : SporociloRepo, SqlRepo<Sporocilo>(name<Sporocilo>()) {
    val id_posiljatelj = reference(Sporocilo::id_posiljatelj.name, KontaktSqlRepo.id)
    val id_prejemnik = reference(Sporocilo::id_prejemnik.name, KontaktSqlRepo.id)
    val vsebina = varchar(Sporocilo::vsebina.name, STR_LONG)

    override fun map(obj: Sporocilo, any: UpdateBuilder<Number>) {
        any[id] = obj.id.value
        any[id_posiljatelj] = obj.id_posiljatelj.value
        any[id_prejemnik] = obj.id_prejemnik.value
        any[vsebina] = obj.vsebina
    }

    override fun resultRow(R: ResultRow): Sporocilo = Sporocilo(
        id = Id(R[id]),
        id_posiljatelj = Id(R[id_posiljatelj]),
        id_prejemnik = Id(R[id_prejemnik]),
        vsebina = R[vsebina],
    )
}
