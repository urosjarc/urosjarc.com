package use_cases

import domain.Kontakt
import domain.Oseba
import kotlinx.serialization.Serializable
import services.EmailService
import services.TelefonService

class Pripravi_kontaktni_obrazec(
    private val telefon: TelefonService,
    private val email: EmailService
) {

    sealed interface Rezultat {
        data class PASS(var oseba: Oseba, var email: Kontakt, var telefon: Kontakt, val vsebina: String) : Rezultat
        data class WARN(val info: String) : Rezultat
    }

    fun zdaj(ime_priimek: String, email: String, telefon: String, vsebina: String): Rezultat {
        /**
         * Ime in priimek validacija
         */
        val imePriimek: String = ime_priimek.trim()
        if (!imePriimek.contains(' '))
            return Rezultat.WARN(info = "Ime ime priimek morata biti ločena z presledkom!")

        val imePriimekList = imePriimek
            .split(" ")
            .map { it.trim().lowercase().replaceFirstChar { it.titlecaseChar() } }
        if (imePriimekList.size != 2)
            return Rezultat.WARN(info = "Ime in priimek morata biti točno dve besedi ločeni z presledkom!")

        /**
         * Vsebina validacija
         */
        val cistaVsebina = vsebina.trim().replace("""\s+""".toRegex(), " ")
        if (cistaVsebina.count { it.equals(' ') } < 2)
            return Rezultat.WARN(info = "Vsebina mora vsebovati vsaj 3 besede!")

        /**
         * Email formatiranje
         */
        val formatiranEmail = when (val r = this.email.formatiraj(email)) {
            is EmailService.RezultatEmailFormatiranja.WARN_EMAIL_NI_PRAVILNE_OBLIKE -> {
                return Rezultat.WARN("Email nima pravilne oblike!")
            }

            is EmailService.RezultatEmailFormatiranja.DATA -> r.email
        }

        /**
         * Telefon formatiranje
         */
        val formatiranTelefon = when (val r = this.telefon.formatiraj(telefon)) {
            is TelefonService.FormatirajRezultat.WARN_TELEFON_NI_PRAVILNE_OBLIKE -> {
                return Rezultat.WARN("Telefon nima pravilne oblike!")
            }

            is TelefonService.FormatirajRezultat.DATA -> r.telefon
        }

        /**
         * Ali email in telefon obstaja
         */
        if (!this.email.obstaja(formatiranEmail))
            return Rezultat.WARN("Email ne obstaja!")
        if (!this.telefon.obstaja(formatiranTelefon))
            return Rezultat.WARN("Telefon ne obstaja!")

        val oseba = Oseba(
            ime = imePriimekList.first(),
            priimek = imePriimekList.last(),
            username = imePriimekList.joinToString("").lowercase(),
            tip = mutableSetOf(Oseba.Tip.KONTAKT)
        )

        return Rezultat.PASS(
            oseba = oseba,
            vsebina = vsebina,
            email = Kontakt(oseba_id = mutableSetOf(oseba._id), data = formatiranEmail, tip = Kontakt.Tip.EMAIL),
            telefon = Kontakt(oseba_id = mutableSetOf(oseba._id), data = formatiranTelefon, tip = Kontakt.Tip.TELEFON)
        )
    }
}
