package si.urosjarc.server.core.use_cases

import org.apache.logging.log4j.kotlin.logger

class Ustvari_template {
    val log = this.logger()

    private fun img_template(width: Int, src: String): String = "<img src='$src' width='$width'>"

    private fun html_template(title: String, image: String, vsebina: String): String = """
        <!DOCTYPE html>
        <html>
            <head>
                <title>$title</title>
            </head>

            <body style ='font-family: monospace; text-align: center;'>
                <table align ='center' border = '0' width = '100%'>
                    <tr>
                        <th align ='center'>
                            ${this.img_template(width = 150, src = image)}
                        </th>
                    </tr>
                    
                    <tr>
                        <td align ='center'>
                            <br>
                            ${vsebina}
                        </td>
                    </tr>
                
                </table>
            </body>
        </html>
        """.trimIndent()

    fun email(ime: String, priimek: String): String = this.html_template(
        title = "Prejeto sporocilo",
        image = "https://avatars.githubusercontent.com/u/105967036?s=200&v=4",
        vsebina = """
                <h1>$ime $priimek</h1>

                <p>Tvoje sporočilo je bilo sprejeto!
                    Zaradi zasedenosti, s klubskimi aktivnostmi lahko pričakuješ naš odgovor najkasneje proti večeru. V primeru nujnosti,
                    nas lahko tudi pokličeš direktno na mobitel številko (odgovor ni garantiran). Če se pa še spomniš, kar si pozabil napisati pa lahko odgovoriš,
                    kar direktno na ta email.
                </p>
                
                <h4>Uros Jarc</h4>
        """.trimIndent()
    )
}
