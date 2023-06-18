package si.programerski_klub.server.core.use_cases

import org.apache.logging.log4j.kotlin.logger
import si.programerski_klub.server.core.domain.uprava.Kontakt

class Sprejmi_kontakt(
    private val validiraj_kontakt: Validiraj_kontakt,
    private val formatiraj_kontakt: Formatiraj_kontakt
) {
    val log = this.logger()

    sealed interface Rezultat {
        object WARN_KONTAKT_NI_PRAVILNE_OBLIKE : Rezultat
        object WARN_KONTAKT_NE_OBSTAJA : Rezultat
        data class DATA(val kontakt: Kontakt) : Rezultat
    }

    fun zdaj(kontakt: Kontakt): Rezultat {

        this.log.debug("Formatiraj kontakt.")
        val formatiran_kontakt: Kontakt = when (val r = this.formatiraj_kontakt.zdaj(kontakt = kontakt)) {
            Formatiraj_kontakt.Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE -> return Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE
            is Formatiraj_kontakt.Rezultat.DATA -> r.kontakt
        }

        this.log.debug("Validiraj kontakt.")
        return when (val r = this.validiraj_kontakt.zdaj(kontakt = formatiran_kontakt)) {
            is Validiraj_kontakt.Rezultat.WARN_KONTAKT_NE_OBSTAJA -> Rezultat.WARN_KONTAKT_NE_OBSTAJA
            is Validiraj_kontakt.Rezultat.DATA -> Rezultat.DATA(kontakt = r.kontakt)
        }
    }
}
