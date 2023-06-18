package si.programerski_klub.server.core.use_cases

import org.apache.logging.log4j.kotlin.logger
import si.programerski_klub.server.core.domain.uprava.Kontakt
import si.programerski_klub.server.core.services.EmailService
import si.programerski_klub.server.core.services.PhoneService

class Formatiraj_kontakt(
    val phone: PhoneService,
    val email: EmailService
) {
    val log = this.logger()

    sealed interface Rezultat {
        object WARN_KONTAKT_NI_PRAVILNE_OBLIKE : Rezultat
        data class DATA(val kontakt: Kontakt) : Rezultat
    }

    fun zdaj(kontakt: Kontakt): Rezultat {

        // TODO: Return FormatiranEmail in FormatiranTelefon
        val formatiran_kontakt = kontakt.copy(
            data = when (kontakt.tip) {
                Kontakt.Tip.EMAIL -> when (val r = this.email.formatiraj(email = kontakt.data)) {
                    is EmailService.RezultatEmailFormatiranja.WARN_EMAIL_NI_PRAVILNE_OBLIKE -> return Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE
                    is EmailService.RezultatEmailFormatiranja.DATA -> r.email.toString()
                }

                Kontakt.Tip.TELEFON -> when (val r = this.phone.formatiraj(telefon = kontakt.data)) {
                    is PhoneService.FormatirajRezultat.WARN_TELEFON_NI_PRAVILNE_OBLIKE -> return Rezultat.WARN_KONTAKT_NI_PRAVILNE_OBLIKE
                    is PhoneService.FormatirajRezultat.DATA -> r.telefon.toString()
                }
            }
        )

        return Rezultat.DATA(kontakt = formatiran_kontakt)
    }
}
