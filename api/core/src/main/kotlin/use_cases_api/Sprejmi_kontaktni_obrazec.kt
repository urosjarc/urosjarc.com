package use_cases_api

import domain.Kontakt
import domain.Oseba
import domain.Sporocilo
import org.apache.logging.log4j.kotlin.logger
import services.DbService
import services.EmailService
import services.TelefonService
import use_cases.Pripravi_kontaktni_obrazec

class Sprejmi_kontaktni_obrazec(
    private val db: DbService,
    private val telefon: TelefonService,
    private val email: EmailService,
    private val pripravi_kontaktni_obrazec: Pripravi_kontaktni_obrazec
) {
    val log = this.logger()

    sealed interface Rezultat {
        object PASS : Rezultat
        data class FAIL(val info: String) : Rezultat
    }

    fun exe(ime_priimek: String, email: String, telefon: String, vsebina: String): Rezultat {

        /**
         * Pripravi obrazec
         */
        val obrazec = when (val r = this.pripravi_kontaktni_obrazec.zdaj(
            ime_priimek = ime_priimek,
            email = email,
            telefon = telefon,
            vsebina = vsebina
        )) {
            is Pripravi_kontaktni_obrazec.Rezultat.PASS -> r
            is Pripravi_kontaktni_obrazec.Rezultat.FAIL -> {
                return Rezultat.FAIL(info = r.info)
            }
        }

        /**
         * Zamenjaj osebo iz obrazca ce ze obstaja!
         */
        when (val r = db.oseba_najdi(
            ime = obrazec.oseba.ime,
            priimek = obrazec.oseba.priimek,
            telefon = obrazec.telefon.data,
            email = obrazec.email.data
        )) {
            null -> db.ustvari(obrazec.oseba)
            else -> obrazec.oseba = r.oseba
        }

        /**
         * Povezi kontakte z id osebe
         */
        obrazec.email.oseba_id = obrazec.oseba._id
        obrazec.telefon.oseba_id = obrazec.oseba._id

        /**
         * Najdi kontakte
         */
        val email_kontakt = when (val r = db.kontakt_najdi(data = obrazec.email.data)) {
            null -> {
                !db.ustvari(obrazec.email)
                obrazec.email
            }

            else -> r
        }
        val telefon_kontakt = when (val r = db.kontakt_najdi(data = obrazec.telefon.data)) {
            null -> {
                !db.ustvari(obrazec.telefon)
                obrazec.telefon
            }

            else -> r
        }

        /**
         * Poslji prvim dostopnim server kontaktom
         */
        val serverji = db.oseba_najdi(tip = Oseba.Tip.SERVER)
        next_kontakt@ for (kontakt in listOf(email_kontakt, telefon_kontakt)) {
            for (serverData in serverji) {
                for (serverKontaktData in serverData.kontakt_refs.filter { it.kontakt.tip == kontakt.tip }) {
                    when (kontakt.tip) {
                        Kontakt.Tip.EMAIL -> {
                            if (this.email.poslji_email(
                                    from = serverKontaktData.kontakt.data,
                                    to = kontakt.data,
                                    subject = "Kontakt",
                                    html = "html"
                                )
                            ) {
                                val sporocilo = Sporocilo(
                                    kontakt_posiljatelj_id = serverKontaktData.kontakt._id,
                                    kontakt_prejemnik_id = kontakt._id,
                                    vsebina = "vsebina"
                                )
                                db.ustvari(sporocilo)
                                continue@next_kontakt
                            }
                        }

                        Kontakt.Tip.TELEFON -> {
                            when (this.telefon.poslji_sms(
                                from = serverKontaktData.kontakt.data,
                                to = kontakt.data,
                                text = "html"
                            )
                            ) {
                                is TelefonService.RezultatSmsPosiljanja.ERROR_SMS_NI_POSLAN -> {}
                                is TelefonService.RezultatSmsPosiljanja.PASS -> {
                                    val sporocilo = Sporocilo(
                                        kontakt_posiljatelj_id = serverKontaktData.kontakt._id,
                                        kontakt_prejemnik_id = kontakt._id,
                                        vsebina = "vsebina"
                                    )
                                    db.ustvari(sporocilo)
                                    continue@next_kontakt
                                }
                            }
                        }
                    }
                }
            }
            log.error("Obvestila o sprejetju kontakta ni bilo mogoce poslati: $kontakt")
        }

        return Rezultat.PASS
    }
}
