package si.urosjarc.server.core.services

interface EmailService {
    sealed interface RezultatEmailFormatiranja {
        object WARN_EMAIL_NI_PRAVILNE_OBLIKE : RezultatEmailFormatiranja
        data class DATA(val email: FormatiranEmail) : RezultatEmailFormatiranja
    }

    @JvmInline
    value class FormatiranEmail(private val value: String)


    fun obstaja(email: FormatiranEmail): Boolean
    fun formatiraj(email: String): RezultatEmailFormatiranja
    fun poslji_email(from: String, to: String, subject: String, html: String): Boolean
//    fun get_all(): List<Email>
//    fun folder_status(folder: String): FolderStatus
//    fun inbox_status(): FolderStatus
//    fun delete(id: String): Boolean
}
