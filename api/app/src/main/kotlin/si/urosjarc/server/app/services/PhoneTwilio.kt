package si.urosjarc.server.app.services

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.twilio.Twilio
import com.twilio.exception.ApiException
import com.twilio.http.TwilioRestClient
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.apache.logging.log4j.kotlin.logger
import si.urosjarc.server.core.services.PhoneService
import com.twilio.rest.lookups.v1.PhoneNumber as PhoneNumberLookup
import com.twilio.type.PhoneNumber as TwilioPhoneNumber


class PhoneTwilio(
    val account_sid: String,
    val auth_token: String,
    val default_region: String,
    val phone: String
) : PhoneService {

    val log = this.logger()
    val twilio: TwilioRestClient
    var phoneUtil: PhoneNumberUtil

    init {
        Twilio.init(this.account_sid, this.auth_token, this.account_sid)
        this.twilio = Twilio.getRestClient()
        this.phoneUtil = PhoneNumberUtil.getInstance()
    }

    override fun obstaja(telefon: PhoneService.FormatiranTelefon): Boolean {
        return try {
            PhoneNumberLookup.fetcher(TwilioPhoneNumber(telefon.toString())).fetch();
            true
        } catch (err: ApiException) {
            this.log.warn(err)
            false
        }
    }

    override fun formatiraj(telefon: String): PhoneService.FormatirajRezultat {
        return try {
            val phone = this.phoneUtil.parse(telefon, this.default_region)
            when (this.phoneUtil.isValidNumber(phone)) {
                false -> PhoneService.FormatirajRezultat.WARN_TELEFON_NI_PRAVILNE_OBLIKE
                true -> PhoneService.FormatirajRezultat.DATA(
                    telefon = PhoneService.FormatiranTelefon(
                        value = this.phoneUtil.format(
                            phone,
                            PhoneNumberUtil.PhoneNumberFormat.E164
                        )
                    )
                )
            }
        } catch (err: NumberParseException) {
            PhoneService.FormatirajRezultat.WARN_TELEFON_NI_PRAVILNE_OBLIKE
        }
    }

    override fun poslji_sms(telefon: PhoneService.FormatiranTelefon, text: String): PhoneService.RezultatSmsPosiljanja {
        val message: Message = Message.creator(PhoneNumber(telefon.value), PhoneNumber(this.phone), text).create()
        val poslan = !listOf(
            Message.Status.CANCELED,
            Message.Status.FAILED,
            Message.Status.UNDELIVERED
        ).contains(message.status) && message.errorCode == null && message.errorMessage == null
        return when (poslan) {
            true -> PhoneService.RezultatSmsPosiljanja.PASS
            false -> PhoneService.RezultatSmsPosiljanja.ERROR_SMS_NI_POSLAN(message.toString())
        }
    }
}
