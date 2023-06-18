package si.programerski_klub.server.core.use_cases_api

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import org.apache.logging.log4j.kotlin.logger
import org.litote.kmongo.Id
import si.programerski_klub.server.core.domain.placevanje.Narocilo
import si.programerski_klub.server.core.domain.placevanje.Narocnina
import si.programerski_klub.server.core.domain.placevanje.Produkt
import si.programerski_klub.server.core.domain.uprava.Kontakt
import si.programerski_klub.server.core.extend.now
import si.programerski_klub.server.core.repos.DbRezultatShranitve
import si.programerski_klub.server.core.services.DbService
import si.programerski_klub.server.core.use_cases.Preveri_zalogo
import si.programerski_klub.server.core.use_cases.Sprejmi_narocnino
import si.programerski_klub.server.core.use_cases.Sprejmi_osebo

class Sprejmi_narocilo(
    val db: DbService,
    val preveri_zalogo: Preveri_zalogo,
    val sprejmi_osebo: Sprejmi_osebo,
    val sprejmi_narocnino: Sprejmi_narocnino
) {

    val log = this.logger()

    sealed interface Rezultat {
        data class DATA(val data: Narocilo) : Rezultat

        object WARN_PREJEMNIK_JE_PLACNIK : Rezultat
        data class WARN_KONTAKT_NI_PRAVILNE_OBLIKE(val kontakt: Kontakt) : Rezultat
        data class WARN_KONTAKT_NE_OBSTAJA(val kontakt: Kontakt) : Rezultat

        object ERROR_PREVELIKO_NAROCILO : Rezultat
        data class ERROR_PRODUKT_NE_OBSTAJA(val id: Id<Produkt>) : Rezultat
        data class ERROR_UPORABNIK_IMA_ZE_NAROCNINO(val narocnina: Narocnina) : Rezultat

        object FATAL_NAROCNINA_SE_NI_SHRANILA : Rezultat
    }

    fun zdaj(narocilo: Narocilo): Rezultat {
        this.log.info("Preveri ce je prejemnik in placnik enaka oseba.")
        narocilo.placnik?.let {
            if (it.enak(narocilo.prejemnik))
                return Rezultat.WARN_PREJEMNIK_JE_PLACNIK
        }

        this.log.info("Preveri ali je dovolj produktov na zalogi.")
        when (val r = this.preveri_zalogo.zdaj(narocilo = narocilo)) {
            is Preveri_zalogo.Rezultat.PASS -> {}
            is Preveri_zalogo.Rezultat.ERROR_PREMAJHNA_ZALOGA -> return Rezultat.ERROR_PREVELIKO_NAROCILO
            is Preveri_zalogo.Rezultat.ERROR_PRODUKT_NE_OBSTAJA -> return Rezultat.ERROR_PRODUKT_NE_OBSTAJA(id = r.id)
        }

        this.log.info("Sprejmi prejemnika.")
        when (val r = this.sprejmi_osebo.zdaj(oseba = narocilo.prejemnik)) {
            is Sprejmi_osebo.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> return Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE(kontakt = r.kontakt)
            is Sprejmi_osebo.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> return Rezultat.WARN_KONTAKT_NE_OBSTAJA(kontakt = r.kontakt)
            is Sprejmi_osebo.Rezultat.DATA -> narocilo.prejemnik = r.oseba
        }

        this.log.info("Sprejmi placnika ce obstaja.")
        narocilo.placnik?.let {
            when (val r = this.sprejmi_osebo.zdaj(oseba = it)) {
                is Sprejmi_osebo.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> return Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE(kontakt = r.kontakt)
                is Sprejmi_osebo.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> return Rezultat.WARN_KONTAKT_NE_OBSTAJA(kontakt = r.kontakt)
                is Sprejmi_osebo.Rezultat.DATA -> narocilo.placnik = r.oseba
            }
        }

        this.log.info("Shrani narocilo v podatkovno bazo.")
        val id_narocila = when (val r = this.db.narocila.shrani(narocilo = narocilo)) {
            is DbRezultatShranitve.DATA -> r.data.id
            is DbRezultatShranitve.FATAL_DB_NAPAKA -> return Rezultat.FATAL_NAROCNINA_SE_NI_SHRANILA
        }

        this.log.info("Sprejem izdelkov v košarici.")
        narocilo.kosarica.forEach {
            when (it.produkt.tip) {
                Produkt.Tip.PROGRAM -> {} //TODO Create new program for user.
                Produkt.Tip.TECAJ -> {}
                Produkt.Tip.PRODUKT -> {}
                Produkt.Tip.CLANARINA -> {}
                Produkt.Tip.NAROCNINA -> when (val r = this.sprejmi_narocnino.zdaj(
                    narocnina = Narocnina(
                        produkt = it.produkt,
                        id_narocila = id_narocila,
                        id_placnika = (narocilo.placnik ?: narocilo.prejemnik).id,
                        id_uporabnika = narocilo.prejemnik.id,
                        frekvenca_racunov = DatePeriod(months = 1),
                        zacetki = setOf(LocalDateTime.now()),
                        konci = setOf(),
                        status = Narocnina.Status.AKTIVNA
                    )
                )) {
                    is Sprejmi_narocnino.Rezultat.ERROR_UPORABNIK_IMA_ZE_NAROCNINO -> {
                        return Rezultat.ERROR_UPORABNIK_IMA_ZE_NAROCNINO(narocnina = r.narocnina)
                    }

                    Sprejmi_narocnino.Rezultat.FATAL_DB_NAPAKA -> {
                        return Rezultat.FATAL_NAROCNINA_SE_NI_SHRANILA
                    }

                    is Sprejmi_narocnino.Rezultat.DATA -> {}
                }

            }
        }

        return Rezultat.DATA(data = narocilo)
    }
}
