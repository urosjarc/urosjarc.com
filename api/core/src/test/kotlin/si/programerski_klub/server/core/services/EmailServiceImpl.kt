package si.programerski_klub.server.core.services

import org.apache.logging.log4j.kotlin.logger

class EmailServiceImpl(
) : EmailService {
    val log = this.logger()

    override fun obstaja(email: EmailService.FormatiranEmail): Boolean {
        this.log.trace("Email obstaja: $email")
        return email.toString().isNotEmpty()
    }

    override fun formatiraj(email: String): EmailService.RezultatEmailFormatiranja {
        this.log.trace("Formatiraj email: $email")
        if (this.obstaja(email = EmailService.FormatiranEmail(value = email)))
            return EmailService.RezultatEmailFormatiranja.DATA(email = EmailService.FormatiranEmail(value = "formated_$email"))
        return EmailService.RezultatEmailFormatiranja.WARN_EMAIL_NI_PRAVILNE_OBLIKE
    }
}
