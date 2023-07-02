package si.urosjarc.server.core.services

import org.apache.logging.log4j.kotlin.logger
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class EmailService(
    val host: String,
    val port: Int,
    val username: String,
    val password: String
) {

    val log = this.logger()
    val properties = Properties()
    val session: Session

    init {
        this.properties.put("mail.smtp.host", host)
        this.properties.put("mail.smtp.port", port)
        this.properties.put("mail.smtp.auth", true)
        this.properties.put("mail.smtp.starttls.enable", true)
        this.properties.put("mail.smtp.ssl.protocols", "TLSv1.2")
        this.properties.put("mail.smtp.ssl.trust", host);
        this.session = Session.getDefaultInstance(this.properties, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })
        this.session.debug = true
    }

    sealed interface RezultatEmailFormatiranja {
        object WARN_EMAIL_NI_PRAVILNE_OBLIKE : RezultatEmailFormatiranja
        data class DATA(val email: FormatiranEmail) : RezultatEmailFormatiranja
    }

    @JvmInline
    value class FormatiranEmail(private val value: String)

    fun obstaja(email: EmailService.FormatiranEmail): Boolean {
        return try {
            val domain = email.toString().split("@").last()
            InetAddress.getByName(domain)
            true
        } catch (err: UnknownHostException) {
            this.log.warn(err)
            false
        }
    }

    fun formatiraj(email: String): EmailService.RezultatEmailFormatiranja {
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

    fun poslji_email(from: String, to: String, subject: String, html: String): Boolean {
        return try {
            val mimeMessage = MimeMessage(session)
            mimeMessage.setFrom(InternetAddress(from))
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, true))
            mimeMessage.setText(html)
            mimeMessage.subject = subject
            mimeMessage.sentDate = Date()

            val smtpTransport = session.getTransport("smtp")
            smtpTransport.connect()
            smtpTransport.sendMessage(mimeMessage, mimeMessage.allRecipients)
            smtpTransport.close()
            true
        } catch (messagingException: MessagingException) {
            messagingException.printStackTrace()
            false
        }
    }


}
