package si.programerski_klub.server.core.services

//import kotlinx.datetime.LocalDateTime


interface EmailService {
//    enum class Flag { SEEN, ANSWERED, FLAGGED, DELETED, DRAFT }
//    interface Sender {
//        val name: String
//        val email: String
//    }
//
//    interface FolderStatus {
//        val messages: Int
//        val recent: Int
//        val unseen: Int
//    }
//
//    interface Email {
//        val id: String
//        val sender: Sender
//        val subject: String
//        val content: String
//        val created: LocalDateTime
//        val flags: List<Flag>
//    }

    sealed interface RezultatEmailFormatiranja {
        object WARN_EMAIL_NI_PRAVILNE_OBLIKE : RezultatEmailFormatiranja
        data class DATA(val email: FormatiranEmail) : RezultatEmailFormatiranja
    }

    @JvmInline
    value class FormatiranEmail(private val value: String) {
        override fun toString(): String = this.value
    }


    fun obstaja(email: FormatiranEmail): Boolean
    fun formatiraj(email: String): RezultatEmailFormatiranja
//    fun send(recipients: List<String>, subject: String, html: String): Boolean
//    fun get_all(): List<Email>
//    fun folder_status(folder: String): FolderStatus
//    fun inbox_status(): FolderStatus
//    fun delete(id: String): Boolean
}
