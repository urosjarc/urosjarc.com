package si.urosjarc.server.core.services

interface TelefonService {
    sealed interface FormatirajRezultat {
        object WARN_TELEFON_NI_PRAVILNE_OBLIKE : FormatirajRezultat
        data class DATA(val telefon: FormatiranTelefon) : FormatirajRezultat
    }

    @JvmInline
    value class FormatiranTelefon(val value: String)

    sealed interface RezultatSmsPosiljanja {
        data class ERROR_SMS_NI_POSLAN(val info: String) : RezultatSmsPosiljanja
        object PASS : RezultatSmsPosiljanja
    }

    fun obstaja(telefon: FormatiranTelefon): Boolean
    fun formatiraj(telefon: String): FormatirajRezultat

    fun poslji_sms(telefon: FormatiranTelefon, text: String): RezultatSmsPosiljanja
}
