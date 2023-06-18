package si.programerski_klub.server.core.use_cases_api

import org.apache.logging.log4j.kotlin.logger
import si.programerski_klub.server.core.domain.obvescanje.KontaktniObrazec
import si.programerski_klub.server.core.domain.uprava.Kontakt
import si.programerski_klub.server.core.repos.DbRezultatShranitve
import si.programerski_klub.server.core.services.DbService
import si.programerski_klub.server.core.use_cases.Sprejmi_kontakt

class Sprejmi_kontakti_obrazec(
    val db: DbService,
    val sprejmi_kontakt: Sprejmi_kontakt
) {

    val log = this.logger()

    sealed interface Rezultat {
        data class WARN_KONTAKT_NI_PRAVILNE_OBLIKE(val kontakt: Kontakt) : Rezultat
        data class WARN_KONTAKT_NE_OBSTAJA(val kontakt: Kontakt) : Rezultat
        object FATAL_KONTAKTNI_OBRAZEC_SE_NI_SHRANIL : Rezultat
        data class DATA(val kontaktni_obrazec: KontaktniObrazec) : Rezultat
    }

    fun zdaj(kontaktni_obrazec: KontaktniObrazec): Rezultat {

        this.log.info("Sprejmi kontni obrazec.")
        val preoblikovani_kontakti = mutableSetOf<Kontakt>()
        kontaktni_obrazec.kontakti.forEach {
            when (val r = this.sprejmi_kontakt.zdaj(kontakt = it)) {
                Sprejmi_kontakt.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> return Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE(kontakt=it)
                Sprejmi_kontakt.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> return Rezultat.WARN_KONTAKT_NE_OBSTAJA(kontakt=it)
                is Sprejmi_kontakt.Rezultat.DATA -> preoblikovani_kontakti.add(r.kontakt)
            }
        }
        kontaktni_obrazec.kontakti = preoblikovani_kontakti

        this.log.info("Shrani kontaktni obrazec.")
        return when (val r = this.db.kontaktni_obrazec.shrani(kontaktni_obrazec = kontaktni_obrazec)) {
            is DbRezultatShranitve.DATA -> Rezultat.DATA(kontaktni_obrazec = r.data)
            is DbRezultatShranitve.FATAL_DB_NAPAKA -> Rezultat.FATAL_KONTAKTNI_OBRAZEC_SE_NI_SHRANIL
        }
    }
}
