package si.programerski_klub.server.core.use_cases

import org.apache.logging.log4j.kotlin.logger
import si.programerski_klub.server.core.domain.uprava.Kontakt
import si.programerski_klub.server.core.services.EmailService
import si.programerski_klub.server.core.services.PhoneService

class Validiraj_kontakt(
    val phone: PhoneService,
    val email: EmailService
) {
    val log = this.logger()

    sealed interface Rezultat {
        data class WARN_KONTAKT_NE_OBSTAJA(val kontakt: Kontakt) : Rezultat
        data class DATA(val kontakt: Kontakt) : Rezultat
    }

    fun zdaj(kontakt: Kontakt): Rezultat {

        val validiran_kontakt = kontakt.copy()

        if (kontakt.nivo_validiranosti == Kontakt.NivoValidiranosti.NI_VALIDIRAN) {
            if (this.kontakt_obstaja(kontakt = kontakt)) {
                validiran_kontakt.nivo_validiranosti = Kontakt.NivoValidiranosti.VALIDIRAN
            } else return Rezultat.WARN_KONTAKT_NE_OBSTAJA(kontakt = kontakt)
        }

        return Rezultat.DATA(kontakt = validiran_kontakt)
    }

    private fun kontakt_obstaja(kontakt: Kontakt): Boolean = when (kontakt.tip) {
        Kontakt.Tip.EMAIL -> this.email.obstaja(email = EmailService.FormatiranEmail(value = kontakt.data))
        Kontakt.Tip.TELEFON -> this.phone.obstaja(telefon = PhoneService.FormatiranTelefon(value = kontakt.data))
    }
}
