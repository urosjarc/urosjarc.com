package core.use_cases

import core.domain.Oseba
import core.domain.Test
import org.apache.logging.log4j.kotlin.logger

class Ustvari_templejt {
    val log = this.logger()

    data class Email(val posiljatelj: String, val subjekt: String, val html: String)

    fun email_potrditev_prejema_kontaktnega_obrazca(
        ime: String,
        priimek: String,
        telefon: String,
        email: String,
        vsebina: String
    ) = Email(
        posiljatelj = "Uroš Jarc",
        subjekt = "$ime $priimek | Vaše sporočilo je bilo sprejeto!",
        html = """
            <br><br><br>
            <div style="font-size:15px;text-align:center;font-family:monospace;">
            
                <h1>📨 Vaše sporočilo je bilo sprejeto! 📨</h1>
                
                <hr width=700>
                
                <p><i>"$ime $priimek", $telefon, $email<br>"$vsebina"</i></p>
                
                <hr width=800>
                
                <p style="font-size: 20px">
                    Odgovor lahko pričakujete v kratkem, najkasneje proti večeru.<br>
                    Preverite če so vse informacije pravilno izpolnjene.<br>
                    Dodatne informacije posredujte v odgovoru.<br>
                    
                    <pre style="margin-top: 8px; font-size:25px">⭐   ⭐   ⭐   ⭐   ⭐</pre>
                </p>
                
                <p style="font-size:25px">Uroš Jarc</p>
            </div>
            <br><br><br>
        """.trimIndent().trim()
    )

    fun sms_potrditev_prejema_kontaktnega_obrazca() = """
        📨 Vaše sporočilo je bilo sprejeto! 📨
        Lp, Uroš Jarc ✨
        """.trimIndent().trim()

    fun email_obvestilo_prejema_testa(prejemnik: Oseba, test: Test) = Email(
        posiljatelj = "Uroš Jarc",
        subjekt = "${prejemnik.ime.decrypt()} ${prejemnik.priimek.decrypt()} | Dodeljen Vam je bil nov test!",
        html = """
            <br><br><br>
            <div style="font-size:15px;text-align:center;font-family:monospace;">
            
                <h1>📝 Dodeljen Vam je nov test! 📝</h1>
                
                <hr width=700>
                
                <div style="font-style: italic">
                    <p style="font-size: 25px; margin-bottom: 0">${test.naslov.decrypt()}</p>
                    <p style="font-size: 22px; margin: 5px">${test.podnaslov.decrypt()}</p>
                    <p style="font-size: 20px; margin-top: 0">
                        Deadline: ${test.deadline},
                        Nalog: ${test.naloga_id.size},
                        Ucencev: ${test.oseba_ucenec_id.size}
                    </p>
                </div>
                
                <hr width=800>
                
                <p style="font-size: 20px">
                    Do testa lahko dostopate preko svojega profila na zavihku testi.
                    
                    <pre style="margin-top: 8px; font-size:25px">⭐   ⭐   ⭐   ⭐   ⭐</pre>
                </p>
                
                <p style="font-size:25px">Uroš Jarc</p>
            </div>
            <br><br><br>
        """.trimIndent().trim()
    )
}

fun main() {
    val t = Ustvari_templejt()
    println(t.sms_potrditev_prejema_kontaktnega_obrazca().length)
}
