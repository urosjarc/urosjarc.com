package si.programerski_klub.server.app.services

import org.apache.logging.log4j.kotlin.logger
import si.programerski_klub.server.core.services.EmailService
import java.net.InetAddress
import java.net.UnknownHostException
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress


class EmailSmtp : EmailService {

    val log = this.logger()

    override fun obstaja(email: EmailService.FormatiranEmail): Boolean {
        return try {
            val domain = email.toString().split("@").last()
            InetAddress.getByName(domain)
            true
        } catch (err: UnknownHostException) {
            this.log.warn(err)
            false
        }
    }

    override fun formatiraj(email: String): EmailService.RezultatEmailFormatiranja {
        val formatiran_email = email.trim()
        try {
            InternetAddress(formatiran_email).validate()
        } catch (ex: AddressException) {
            return EmailService.RezultatEmailFormatiranja.WARN_EMAIL_NI_PRAVILNE_OBLIKE
        }
        return EmailService.RezultatEmailFormatiranja.DATA(
            email = EmailService.FormatiranEmail(value = formatiran_email)
        )
    }

}
