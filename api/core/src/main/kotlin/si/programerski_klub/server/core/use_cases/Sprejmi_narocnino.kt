package si.programerski_klub.server.core.use_cases

import org.apache.logging.log4j.kotlin.logger
import si.programerski_klub.server.core.domain.placevanje.Narocnina
import si.programerski_klub.server.core.domain.placevanje.Produkt
import si.programerski_klub.server.core.repos.DbRezultatIskanja
import si.programerski_klub.server.core.repos.DbRezultatShranitve
import si.programerski_klub.server.core.services.DbService

class Sprejmi_narocnino(
    val db: DbService,
) {
    val log = this.logger()

    sealed interface Rezultat {
        data class ERROR_UPORABNIK_IMA_ZE_NAROCNINO(val narocnina: Narocnina) : Rezultat
        object FATAL_DB_NAPAKA : Rezultat
        data class DATA(val narocnina: Narocnina) : Rezultat
    }

    fun zdaj(narocnina: Narocnina): Rezultat {

        this.log.debug("Preveri ali uporabnik slucajno ni ponesreci že vpisan na izbrano narocnino (produkt.tip)")
        when (val r = this.db.narocnine.ena(id = narocnina.id_uporabnika, tip = narocnina.produkt.tip)) {
            is DbRezultatIskanja.PASS -> {}
            is DbRezultatIskanja.DATA -> return Rezultat.ERROR_UPORABNIK_IMA_ZE_NAROCNINO(narocnina = r.data)
        }

        this.log.debug("Shrani naročnino v podatkovno bazo.")
        when (this.db.narocnine.shrani(narocnine = narocnina)) {
            is DbRezultatShranitve.DATA -> {}
            is DbRezultatShranitve.FATAL_DB_NAPAKA -> return Rezultat.FATAL_DB_NAPAKA
        }

        this.log.debug("Izvedi posebno logiko za razlicne tipe narocnine.")
        when (narocnina.produkt.tip) {
            Produkt.Tip.NAROCNINA -> {}
            Produkt.Tip.TECAJ -> {}
            Produkt.Tip.PRODUKT -> {}
            Produkt.Tip.PROGRAM -> TODO()
            Produkt.Tip.CLANARINA -> TODO()
        }

        this.log.debug("V primeru posebne naročnine izvedi dodatne stvari. kot primer povabilo v discord etc...")
        return Rezultat.DATA(narocnina = narocnina)

    }
}
