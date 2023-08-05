package use_cases_api

import base.Encrypted
import base.encrypt
import domain.Kontakt
import domain.Oseba
import domain.Sporocilo
import org.apache.logging.log4j.kotlin.logger
import services.DbService
import services.EmailService
import services.TelefonService
import use_cases.Pripravi_kontaktni_obrazec
import use_cases.Ustvari_templejt

class Sprejmi_kontaktni_obrazec(
    private val db: DbService,
    private val telefon: TelefonService,
    private val email: EmailService,
    private val pripravi_kontaktni_obrazec: Pripravi_kontaktni_obrazec,
    private val ustvari_template: Ustvari_templejt
) {
    val log = this.logger()

    sealed interface Rezultat {
        data class PASS(
            val obrazec: Pripravi_kontaktni_obrazec.Rezultat.PASS,
            val sporocila: List<Sporocilo>
        ) : Rezultat

        data class WARN(val info: String) : Rezultat
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
            is Pripravi_kontaktni_obrazec.Rezultat.WARN -> {
                return Rezultat.WARN(info = r.info)
            }
        }

        /**
         * Pripravi osebo
         */
        when (val r = db.oseba_najdi(
            ime = obrazec.oseba.ime.toString(),
            priimek = obrazec.oseba.priimek.toString(),
            telefon = obrazec.telefon.data.toString(),
            email = obrazec.email.data.toString()
        )) {
            null -> db.ustvari(obrazec.oseba)
            else -> obrazec.oseba = r.oseba
        }

        /**
         * Pripravi email kontakt
         */
        when (val r = db.kontakt_najdi(data = obrazec.email.data.toString())) {
            null -> obrazec.email.oseba_id.add(obrazec.oseba._id)
            else -> obrazec.email = r
        }

        /**
         * Pripravi telefon kontakt
         */
        when (val r = db.kontakt_najdi(data = obrazec.telefon.data.toString())) {
            null -> obrazec.telefon.oseba_id.add(obrazec.oseba._id)
            else -> obrazec.telefon = r
        }

        /**
         * Najdi server osebe za sporocila
         */
        val serverji = db.oseba_najdi(tip = Oseba.Tip.SERVER)
        val sporocila = mutableListOf<Sporocilo>()

        /**
         * Razposlji sporocila
         */
        next_kontakt@ for (kontakt in listOf(obrazec.email, obrazec.telefon)) {
            for (serverData in serverji) {
                for (serverKontaktData in serverData.kontakt_refs.filter { it.kontakt.tip == kontakt.tip }) {
                    when (kontakt.tip) {
                        Kontakt.Tip.EMAIL -> {
                            val template = ustvari_template.email_potrditev_prejema_kontaktnega_obrazca(
                                ime = obrazec.oseba.ime.toString(),//Todo!
                                priimek = obrazec.oseba.priimek.toString(),
                                telefon = obrazec.telefon.data.toString(),
                                email = obrazec.email.data.toString(),
                                vsebina = obrazec.vsebina
                            )
                            if (this.email.poslji_email(
                                    fromName = template.posiljatelj,
                                    from = serverKontaktData.kontakt.data.toString(),
                                    to = kontakt.data.toString(),
                                    subject = template.subjekt,
                                    html = template.html
                                )
                            ) {
                                val sporocilo = Sporocilo(
                                    kontakt_posiljatelj_id = serverKontaktData.kontakt._id,
                                    kontakt_prejemnik_id = mutableSetOf(kontakt._id),
                                    vsebina = template.html.encrypt()
                                )
                                db.ustvari(sporocilo)
                                sporocila.add(sporocilo)
                                continue@next_kontakt
                            }
                        }

                        Kontakt.Tip.TELEFON -> {
                            val template = ustvari_template.sms_potrditev_prejema_kontaktnega_obrazca()
                            when (this.telefon.poslji_sms(
                                from = serverKontaktData.kontakt.data.decript(),
                                to = kontakt.data.decript(),
                                text = template
                            )
                            ) {
                                is TelefonService.RezultatSmsPosiljanja.ERROR_SMS_NI_POSLAN -> {}
                                is TelefonService.RezultatSmsPosiljanja.PASS -> {
                                    val sporocilo = Sporocilo(
                                        kontakt_posiljatelj_id = serverKontaktData.kontakt._id,
                                        kontakt_prejemnik_id = mutableSetOf(kontakt._id),
                                        vsebina = template.encrypt()
                                    )
                                    db.ustvari(sporocilo)
                                    sporocila.add(sporocilo)
                                    continue@next_kontakt
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * Shrani vse skupaj v podatkovno bazo!
         */
        return Rezultat.PASS(obrazec = obrazec, sporocila = sporocila)
    }
}
