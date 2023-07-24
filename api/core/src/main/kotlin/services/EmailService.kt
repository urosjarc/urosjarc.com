package services

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
        data class DATA(val email: String) : RezultatEmailFormatiranja
    }

    fun formatiraj(email: String): RezultatEmailFormatiranja {
        val formatiran_email = email.trim().lowercase()
        try {
            InternetAddress(formatiran_email).validate()
        } catch (ex: AddressException) {
            return RezultatEmailFormatiranja.WARN_EMAIL_NI_PRAVILNE_OBLIKE
        }
        return RezultatEmailFormatiranja.DATA(email = formatiran_email)
    }

    fun obstaja(email: String): Boolean {
        return try {
            val domain = email.split("@").last()
            InetAddress.getByName(domain)
            true
        } catch (err: UnknownHostException) {
            this.log.warn(err)
            false
        }
    }

    fun poslji_email(from: String, to: String, subject: String, html: String): Boolean {
        return try {
            val fromAddresses = InternetAddress(from)
            val toAddress = InternetAddress(to)

            val mimeMessage = MimeMessage(session)
            mimeMessage.setFrom(fromAddresses)
            mimeMessage.addRecipient(Message.RecipientType.TO, toAddress)

            mimeMessage.setContent(html, "text/html; charset=utf-8");
            mimeMessage.subject = subject
            mimeMessage.sentDate = Date()

            val smtpTransport = session.getTransport("smtp")
            smtpTransport.connect()
            smtpTransport.sendMessage(mimeMessage, arrayOf(fromAddresses, toAddress))
            smtpTransport.close()
            true
        } catch (messagingException: MessagingException) {
            messagingException.printStackTrace()
            false
        }
    }


}
