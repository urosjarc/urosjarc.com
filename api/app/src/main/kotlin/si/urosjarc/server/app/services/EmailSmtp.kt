package si.urosjarc.server.app.services

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.twilio.http.TwilioRestClient
import org.apache.logging.log4j.kotlin.logger
import org.simplejavamail.api.email.Email
import org.simplejavamail.api.mailer.Mailer
import org.simplejavamail.api.mailer.config.TransportStrategy
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder
import si.urosjarc.server.core.services.EmailService
import java.lang.RuntimeException
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress


class EmailSmtp(
    val host: String,
    val port: Int,
    val username: String,
    val password: String
) : EmailService {

    val log = this.logger()
    val mailer: Mailer

    init {
        log.error("$host $port $username $password")
        this.mailer = MailerBuilder.withSMTPServer(this.host, this.port, this.username, this.password)
            .withDebugLogging(true)
            .withTransportStrategy(TransportStrategy.SMTP)
            .buildMailer()
        try {
            this.mailer.testConnection()
        } catch (err: RuntimeException){
            log.error("ERROR" + err.toString())

        }
    }

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

    override fun poslji_email(from: String, to: String, subject: String, html: String): Boolean {
        val test = this.mailer.sendMail(
            EmailBuilder.startingBlank()
                .from(from)
                .to(to)
                .withSubject(subject)
                .withPlainText(html)
                .withHTMLText(html)
                .buildEmail()
        ).get(10, TimeUnit.SECONDS).toString()
        println(test)
        return true
    }


}
