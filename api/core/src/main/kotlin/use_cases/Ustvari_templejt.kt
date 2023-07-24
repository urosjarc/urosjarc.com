package use_cases

import org.apache.logging.log4j.kotlin.logger

class Ustvari_templejt {
    val log = this.logger()

    data class Email(val subjekt: String, val html: String)

    fun email_potrditev_prejema_kontaktnega_obrazca(
        ime: String,
        priimek: String,
        telefon: String,
        email: String,
        vsebina: String
    ) = Email(
        subjekt = "Uroš Jarc | Vaš kotakt je bil sprejet!",
        html = """
            <br><br><br>
            <div style="font-size:15px;text-align:center;font-family:monospace;">
                <h1>📨 Vaše sporočilo je bilo sprejeto! 📨</h1>
                <hr width=700>
                <p><i>
                    "$ime $priimek", $telefon, $email
                    <br>
                    "$vsebina"
                </i></p>
                <hr width=800>
                <p style="font-size: 20px">
                    Odgovor lahko pričakujete v kratkem, najkasneje proti večeru.<br>
                    Preverite če so vse informacije pravilno izpolnjene.<br>
                    Dodatne informacije posredujte v odgovoru.
                </p>
                <hr width=350>
                <p style="font-size:25px">Lp, Uroš Jarc<br>⭐</p>
            </div>
            <br><br><br>
        """.trimIndent().trim()
    )

    fun sms_potrditev_prejema_kontaktnega_obrazca() = """
        📨 Vaše sporočilo je bilo sprejeto! 📨
        
        Odgovor lahko pričakujete v kratkem, najkasneje proti večeru.
        Preverite na email sporocilu, če so vse informacije pravilno izpolnjene.
            
        Lp, Uroš Jarc ⭐
        """.trimIndent().trim()
}
