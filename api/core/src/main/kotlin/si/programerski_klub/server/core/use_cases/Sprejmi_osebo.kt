package si.programerski_klub.server.core.use_cases

import org.apache.logging.log4j.kotlin.logger
import si.programerski_klub.server.core.domain.uprava.Kontakt
import si.programerski_klub.server.core.domain.uprava.Oseba
import si.programerski_klub.server.core.services.DbService

class Sprejmi_osebo(
    val db: DbService,
    val sprejmi_kontakt: Sprejmi_kontakt
) {
    val log = this.logger()

    sealed interface Rezultat {
        data class WARN_KONTAKT_NI_PRAVILNE_OBLIKE(val kontakt: Kontakt) : Rezultat
        data class WARN_KONTAKT_NE_OBSTAJA(val kontakt: Kontakt) : Rezultat
        data class DATA(val oseba: Oseba) : Rezultat
    }

    fun zdaj(oseba: Oseba): Rezultat {
        this.log.debug("Sprejmi kontakte osebe.")
        val preoblikovani_kontakti = mutableSetOf<Kontakt>()
        oseba.kontakti.forEach {
            when (val r = this.sprejmi_kontakt.zdaj(kontakt = it)) {
                Sprejmi_kontakt.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> return Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE(kontakt = it)
                Sprejmi_kontakt.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> return Rezultat.WARN_KONTAKT_NE_OBSTAJA(kontakt = it)
                is Sprejmi_kontakt.Rezultat.DATA -> preoblikovani_kontakti.add(r.kontakt)
            }
        }
        oseba.kontakti = preoblikovani_kontakti

        return Rezultat.DATA(oseba = oseba)
    }
}
