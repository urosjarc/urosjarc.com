package si.programerski_klub.server.core.services

interface PhoneService {
    sealed interface FormatirajRezultat {
        object WARN_TELEFON_NI_PRAVILNE_OBLIKE : FormatirajRezultat
        data class DATA(val telefon: FormatiranTelefon) : FormatirajRezultat
    }

    @JvmInline
    value class FormatiranTelefon(private val value: String) {
        override fun toString(): String = this.value
    }

    fun obstaja(telefon: FormatiranTelefon): Boolean
    fun formatiraj(telefon: String): FormatirajRezultat
}
